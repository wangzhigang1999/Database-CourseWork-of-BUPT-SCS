package com.bupt.database.aspect;

import com.bupt.database.annotation.AuthCheck;
import com.bupt.database.pojo.RequestPojo;
import com.bupt.database.pojo.Resp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

/**
 * @author wangz
 */
@Component
@Aspect
public class LoggerAspect {
    Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    final MongoTemplate mongoTemplate;

    public LoggerAspect(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Pointcut("execution(* com.bupt.database.service.*.*(..))")
    public void logPointCut() {
    }

    @Pointcut("@annotation(com.bupt.database.annotation.AuthCheck)")
    public void authPointCut() {
    }

    @Before("logPointCut()")
    public void beforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        mongoTemplate.insert(new RequestPojo(
                request.getRequestURL().toString(),
                request.getRemoteAddr(),
                parameterMap,
                request.getMethod(),
                (joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()),
                Arrays.toString(joinPoint.getArgs())
        ));

        startTime.set(System.currentTimeMillis());
    }

    @Around("authPointCut() && @annotation(authCheck)")
    public Object beforeAuth(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (Arrays.stream(authCheck.role()).noneMatch((i) -> i.equals(role))) {
            logger.info("Authorization failed");
            return new Resp(50014, "Unauthorized");
        }
        logger.info("Authorization success username ={} role={}", session.getAttribute("username"), role);
        return joinPoint.proceed();
    }

    @AfterReturning(returning = "object", pointcut = "logPointCut()")
    public void after(Object object) {
        mongoTemplate.insert(object);
        logger.info(object.toString());
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}

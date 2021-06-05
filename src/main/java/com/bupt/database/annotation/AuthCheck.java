package com.bupt.database.annotation;

import java.lang.annotation.*;

/**
 * @author wanz
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthCheck {
    //所操作的业务对应的权限ID
    String[] role();
}
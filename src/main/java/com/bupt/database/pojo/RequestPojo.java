package com.bupt.database.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wangz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPojo {
    private String reqUrl;
    private String remoteAddr;
    private Map<String, String[]> reqParams;
    private String httpMethod;
    private String classMethod;
    private String functionArgs;

}

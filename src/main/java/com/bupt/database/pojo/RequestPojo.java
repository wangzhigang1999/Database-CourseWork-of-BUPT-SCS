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
    private Map<String, String[]> reqParms;
    private String httpMethord;
    private String classMethord;
    private String functionArgs;

}

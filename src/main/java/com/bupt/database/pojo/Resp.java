package com.bupt.database.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanz
 */
@Data
@NoArgsConstructor
public class Resp {
    int code = 0;
    boolean message = true;
    Object detail ;


    public Resp(Object detail) {
        this.detail = detail;
    }

    public Resp(int code, String detail) {
        this.code = code;
        this.detail = detail;
        message = false;
    }
}

package com.bupt.database.util;

import com.google.gson.Gson;

import java.util.HashMap;

public class InterceptUtil {
    public static Object intercept(Object data) {
        String data_s = new Gson().toJson(data);
        String excel_url = ConnectPythonUtil.convert(data_s);
        HashMap<String, Object> res = new HashMap<>();
        res.put("download_url", excel_url);
        res.put("data", data);
        return res;
    }
}

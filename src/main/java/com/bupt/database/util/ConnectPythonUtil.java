package com.bupt.database.util;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;

import java.util.HashMap;

public class ConnectPythonUtil {
    public final static String IP_ADDRESS = "http://10.128.241.52:5000/";

    @SneakyThrows
    public static String connect(HashMap<String, String> keyValueMap, String router) {
        var execute = Jsoup.connect(IP_ADDRESS + router)
                .ignoreContentType(true)
                .data(keyValueMap)
                .timeout(0)
                .execute();
        return execute.body();
    }

    @SneakyThrows
    public static String convert(String data) {
        var put = new HashMap<String, String>();
        put.put("data", data);
        org.jsoup.Connection.Response execute = Jsoup.connect(IP_ADDRESS + "convertJsonToExcel").timeout(0).data(put).execute();
        return execute.body();
    }
}

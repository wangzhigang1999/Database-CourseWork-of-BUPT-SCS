package com.bupt.database.util;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;

import java.util.HashMap;

public class ConnectPythonUtil {
    @SneakyThrows
    public static String connect(HashMap<String, String> keyValueMap, String router) {
        org.jsoup.Connection.Response execute = Jsoup.connect("http://127.0.0.1:5000/" + router).data(keyValueMap).execute();
        return execute.body();
    }

    @SneakyThrows
    public static String convert(String data) {
        var put = new HashMap<String, String>();
        put.put("data", data);
        org.jsoup.Connection.Response execute = Jsoup.connect("http://127.0.0.1:5000/" + "convertJsonToExcel").timeout(0).data(put).execute();
        return execute.body();
    }
}

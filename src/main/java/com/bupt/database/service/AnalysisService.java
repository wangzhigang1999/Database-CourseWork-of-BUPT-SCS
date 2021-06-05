package com.bupt.database.service;

import com.bupt.database.pojo.Resp;
import com.bupt.database.util.ConnectPythonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AnalysisService {
    public Object analysis7(String threshold) {
        HashMap<String, String> map = new HashMap<>();
        map.put("threshold", threshold);
        String query = ConnectPythonUtil.connect(map, "analysis7");
        return new Resp(query);
    }

    public Object analysis8(String x) {
        HashMap<String, String> map = new HashMap<>();
        map.put("x", x);
        String query = ConnectPythonUtil.connect(map, "analysis8");
        return new Resp(query);
    }

    public Object analysis9() {
        HashMap<String, String> map = new HashMap<>();
        String query = ConnectPythonUtil.connect(map, "analysis9");
        return new Resp(query);
    }
}

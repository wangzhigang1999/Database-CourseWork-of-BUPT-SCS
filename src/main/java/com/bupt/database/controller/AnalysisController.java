package com.bupt.database.controller;

import com.bupt.database.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/database")
public class AnalysisController {
    @Autowired
    AnalysisService service;

    @RequestMapping("/analysis7")
    public Object analysis7(String threshold) {
        return service.analysis7(threshold);
    }

    @RequestMapping("/analysis8")
    public Object analysis8(String x) {
        return service.analysis8(x);
    }

    @RequestMapping("/analysis9")
    public Object analysis8() {
        return service.analysis9();
    }
}

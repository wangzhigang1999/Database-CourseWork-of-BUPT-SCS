package com.bupt.database.controller;

import com.bupt.database.service.DbInfoService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/database")
public class DbInfoController {
    final DbInfoService service;

    public DbInfoController(DbInfoService service) {
        this.service = service;
    }


    @GetMapping("/getDbInfo")
    public Object getInfo(String key) {
        return service.getTimeoutInfo(key);
    }

    @PostMapping("/updateDbInfo")
    public Object updateDbInfo(@RequestParam MultiValueMap<String, String> parameters) {
        return service.updateDbInfo(parameters);
    }
}

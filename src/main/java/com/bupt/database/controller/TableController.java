package com.bupt.database.controller;

import com.bupt.database.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wanz
 */
@RestController
@RequestMapping("/database")
public class TableController {

    @Autowired
    TableService service;

    @RequestMapping("/getTableList")
    public Object getTableList() {
        return service.getTables();
    }

    @RequestMapping("/uploadTable")
    public Object insertTable(String tableName, MultipartFile file) {
        return service.insertToTable(tableName, file);
    }

    @RequestMapping("/exportTable")
    public Object exportTable(String tableName) {
        return service.exportTable(tableName);
    }

}

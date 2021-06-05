package com.bupt.database.controller;

import com.bupt.database.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wanz
 */
@RestController
@RequestMapping("/database")
public class QueryController {
    @Autowired
    QueryService service;

    @RequestMapping("/query")
    public Object query(HttpServletRequest request) {
        return service.query(request);
    }

    @RequestMapping("/getCommunities")
    public Object getCommunities(String tableName) {
        return service.getCommunities(tableName);
    }

    @RequestMapping("/getCommunity")
    public Object getCommunity(String community) {
        return service.getCommunity(community);
    }

    @RequestMapping("/getAllFields")
    public Object getAllFields(String tableName) {
        return service.getTableCol(tableName);
    }

    @RequestMapping("/CommunityKPIIndicatorInformationQuery")
    public Object communityKPIIndicatorInformationQuery(String startTimeStamp, String endTimeStamp, String field,String communityName) {
        return service.communityKPIIndicatorInformationQuery(startTimeStamp, endTimeStamp, field,communityName);
    }

    @RequestMapping("/getEnodeBID")
    public Object getEnodeBID() {
        return service.getEnodeBID();
    }

    @RequestMapping("/getEnodeBName")
    public Object getEnodeBName() {
        return service.getEnodeBName();
    }

    @RequestMapping("/getVisualableFieldsFromKpi")
    public Object getVisualableFieldsFromKpi() {
        return service.getVisualableFieldsFromKpi();
    }


    @RequestMapping("/getSectorId")
    public Object getSectorId() {
        return service.getSectorId();
    }

    @RequestMapping("/getSectorName")
    public Object getSectorName() {
        return service.getSectorNAME();
    }
}

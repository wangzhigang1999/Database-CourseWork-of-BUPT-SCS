package com.bupt.database.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bupt.database.mapper.*;
import com.bupt.database.pojo.Cell;
import com.bupt.database.pojo.Kpi;
import com.bupt.database.pojo.Resp;
import com.bupt.database.util.ConnectPythonUtil;
import com.bupt.database.util.InterceptUtil;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * @author wanz
 */
@Service
public class QueryService {

    final CellMapper cellMapper;
    final KpiMapper kpiMapper;
    final UserMapper userMapper;
    final PrbMapper prbMapper;
    final MroDataMapper mroDataMapper;
    final DataSource dataSource;

    public QueryService(CellMapper cellMapper, KpiMapper kpiMapper, UserMapper userMapper, PrbMapper prbMapper, MroDataMapper mroDataMapper, DataSource dataSource) {
        this.cellMapper = cellMapper;
        this.kpiMapper = kpiMapper;
        this.userMapper = userMapper;
        this.prbMapper = prbMapper;
        this.mroDataMapper = mroDataMapper;
        this.dataSource = dataSource;
    }

    /**
     * 通用查询,可以查询指定表的任意字段
     *
     * @param request 请求头
     * @return res
     */
    public Resp query(HttpServletRequest request) {

        String currentPage = request.getParameter("currentPage");
        String tableName = request.getParameter("tableName");
        Map<String, String[]> map = request.getParameterMap();


        switch (tableName) {
            case "cell" -> {

                QueryWrapper<Cell> wrapper = new QueryWrapper<>();
                map.forEach((a, b) -> {
                    if (!"tableName".equals(a) && !"currentPage".equals(a)) {
                        wrapper.eq(a, b[0]);
                    }
                });

                Page<Cell> page = new Page<>();
                page.setSize(50);
                page.setCurrent(Integer.parseInt(currentPage));
                Page<Cell> cellPage = cellMapper.selectPage(page, wrapper);

                long current = cellPage.getCurrent();
                long totalPages = cellPage.getPages();
                List<Cell> records = cellPage.getRecords();
                HashMap<Object, Object> hashMap = new HashMap<>(16);
                hashMap.put("current", current);
                hashMap.put("total_pages", totalPages);
                hashMap.put("records", records);
                return new Resp(hashMap);

            }
            case "kpi" -> {
                QueryWrapper<Kpi> wrapper = new QueryWrapper<>();
                map.forEach((a, b) -> {
                    if (!"tableName".equals(a) && !"currentPage".equals(a)) {
                        wrapper.eq(a, b[0]);
                    }
                });

                Page<Kpi> page = new Page<>();
                page.setSize(50);
                page.setCurrent(Integer.parseInt(currentPage));
                Page<Kpi> cellPage = kpiMapper.selectPage(page, wrapper);

                long current = cellPage.getCurrent();
                long totalPages = cellPage.getPages();
                List<Kpi> records = cellPage.getRecords();
                HashMap<Object, Object> hashMap = new HashMap<>(16);
                hashMap.put("current", current);
                hashMap.put("total_pages", totalPages);
                hashMap.put("records", records);
                return new Resp(hashMap);
            }
            default -> {
                return new Resp(-1, "请指定正确的表名");
            }

        }


    }

    /**
     * 获取所有的社区,未分页
     *
     * @param tableName 表名,指定获取哪张表包含的社区名称
     * @return 所有的社区名称, 未分页
     */
    public Resp getCommunities(String tableName) {
        HashSet<String> set = new HashSet<>();
        if ("cell".equals(tableName)) {
            List<String> list = cellMapper.getCommunity();
            for (String s : list) {
                set.add(s.split("-")[0]);
            }
        } else if ("kpi".equals(tableName)) {
            List<String> list = kpiMapper.getCommunity();
            for (String s : list) {
                set.add(s.split("-")[0]);
            }
        } else if ("prb".equals(tableName)) {
            List<String> list = prbMapper.getCommunity();
            for (String s : list) {
                set.add(s.split("-")[0]);
            }
        }

        var res = InterceptUtil.intercept(set);
        return new Resp(res);

    }


    /**
     * 获取指定小区的全部信息
     *
     * @param community 指定的小区名称
     * @return
     */
    public Resp getCommunity(String community) {
        QueryWrapper<Cell> wrapper = new QueryWrapper<>();
        wrapper.like("SECTOR_NAME", community);

        List<Cell> cells = cellMapper.selectList(wrapper);
        var res = InterceptUtil.intercept(cells);

        return new Resp(res);
    }

    @SneakyThrows
    public Resp getTableCol(String tableName) {
        List<String> columnNames = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from %s".formatted(tableName));
        ResultSetMetaData metaData = statement.getMetaData();
        // 将表的所有字段提取出来
        int size = metaData.getColumnCount();
        for (int i = 0; i < size; i++) {
            columnNames.add(metaData.getColumnName(i + 1));
        }

        var res = InterceptUtil.intercept(columnNames);
        return new Resp(res);
    }

    public Resp communityKPIIndicatorInformationQuery(String startTimeStamp, String endTimeStamp, String field, String communityName) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("startTimeStamp", startTimeStamp);
        stringStringHashMap.put("endTimeStamp", endTimeStamp);
        stringStringHashMap.put("field", field);
        stringStringHashMap.put("communityName", communityName);

        String query = ConnectPythonUtil.connect(stringStringHashMap, "communityKPIIndicatorInformationQuery");

        System.out.println(query);

        return new Resp(query);
    }


    public Resp getEnodeBID() {
        List<String> enodeBID = cellMapper.getEnodeBID();

        var res = InterceptUtil.intercept(enodeBID);
        return new Resp(res);
    }

    public Resp getEnodeBName() {
        List<String> enodeBName = cellMapper.getEnodeBName();
        var res = InterceptUtil.intercept(enodeBName);
        return new Resp(res);
    }

    @SneakyThrows
    public Object getVisualableFieldsFromKpi() {

        List<String> columnNames = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from %s".formatted("kpi"));
        ResultSetMetaData metaData = statement.getMetaData();
        // 将表的所有字段提取出来
        int size = metaData.getColumnCount();
        for (int i = 0; i < size; i++) {
            columnNames.add(metaData.getColumnName(i + 1));
        }

        columnNames.remove("起始时间");
        columnNames.remove("网元基站名称");
        columnNames.remove("小区");
        columnNames.remove("小区名称");

        var res = InterceptUtil.intercept(columnNames);
        return new Resp(res);
    }


    public Object getSectorId() {
        List<String> sectorid = cellMapper.getSECTORID();
        var res = InterceptUtil.intercept(sectorid);
        return new Resp(res);
    }

    public Object getSectorNAME() {
        List<String> sector_name = cellMapper.getSECTOR_NAME();
        var res = InterceptUtil.intercept(sector_name);
        return new Resp(res);
    }
}

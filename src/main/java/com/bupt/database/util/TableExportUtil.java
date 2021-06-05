package com.bupt.database.util;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bupt.database.mapper.KpiMapper;
import com.bupt.database.pojo.Kpi;
import com.bupt.database.pojo.Resp;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wanz
 */
@Component
public class TableExportUtil {

    @Autowired
    DataSource dataSource;

    final static int PAGE_SIZE = 100;

    String targetDir = System.getProperties().getProperty("user.dir") + "/export_file/";


    @SneakyThrows
    public Resp export(String tableName, String targetName, BaseMapper dao) {
        ArrayList<String> columnNames = new ArrayList<>();
        Connection connection = null;

        connection = dataSource.getConnection();

        PreparedStatement statement = null;

        statement = connection.prepareStatement("select * from %s".formatted(tableName));

        ResultSetMetaData metaData = null;

        metaData = statement.getMetaData();

        // 将表的所有字段提取出来
        int size = metaData.getColumnCount();

        for (int i = 0; i < size; i++) {

            columnNames.add(metaData.getColumnName(i + 1));

        }

        HashMap<String, String> tableInfo = new HashMap<>();
        Integer recordCnt = dao.selectCount(null);


        tableInfo.put("recordCnt", recordCnt.toString());
        tableInfo.put("columnInfo", String.valueOf(columnNames));


        String targetPath = targetDir + targetName;

        try (PrintWriter writer = new PrintWriter(new FileWriter(targetPath, false))) {
            writer.println(tableInfo);

            Page<Object> page = new Page<>(0, PAGE_SIZE, false);
            long totalPages = recordCnt.longValue() / PAGE_SIZE + 1;
            for (long i = 1; i <= totalPages; i++) {
                page.setCurrent(i);
                IPage iPage = dao.selectPage(page, null);
                for (Object record : iPage.getRecords()) {
                    writer.println(new Gson().toJson(record));
                }
            }

            writer.flush();


        } catch (IOException e) {
            return new Resp(-1, "导出失败: %s".formatted(e.getMessage()));
        }

        HashMap<String, String> res = new HashMap<>(16);
        res.put("downloadUrl", "/database/" + targetName);
        res.put("totalCnt", recordCnt.toString());
        return new Resp(res);
    }


    @SneakyThrows
    public Resp export(String tableName, String targetName, KpiMapper dao) {
        ArrayList<String> columnNames = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from %s".formatted(tableName));
        ResultSetMetaData metaData = statement.getMetaData();

        // 将表的所有字段提取出来
        int size = metaData.getColumnCount();
        for (int i = 0; i < size; i++) {
            columnNames.add(metaData.getColumnName(i + 1));
        }

        HashMap<String, String> tableInfo = new HashMap<>();
        Integer recordCnt = dao.selectCount(null);


        tableInfo.put("recordCnt", recordCnt.toString());
        tableInfo.put("columnInfo", String.valueOf(columnNames));


        String targetPath = targetDir + targetName;

        try (PrintWriter writer = new PrintWriter(new FileWriter(targetPath, false))) {
            writer.println(tableInfo);

            int offset = 0;
            for (int i = 0; i < recordCnt / PAGE_SIZE; i++) {
                List<Kpi> kpis = dao.selectRange(PAGE_SIZE, offset);
                writer.println(new Gson().toJson(kpis));
                offset += PAGE_SIZE;
            }
            writer.flush();

        } catch (IOException e) {
            return new Resp(-1, "导出失败: %s".formatted(e.getMessage()));
        }

        HashMap<String, String> res = new HashMap<>(16);
        res.put("downloadUrl", "/database/" + targetName);
        res.put("totalCnt", recordCnt.toString());
        return new Resp(res);
    }
}

package com.bupt.database.service;

import com.bupt.database.mapper.*;
import com.bupt.database.pojo.Resp;
import com.bupt.database.util.ConnectPythonUtil;
import com.bupt.database.util.TableExportUtil;
import com.bupt.database.util.XlsxInsert;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wanz
 */
@Component
public class TableService {
    final static String SAVE_DIR = "./";

    final DataSource dataSource;

    final XlsxInsert xlsxInsert;

    final TableExportUtil tableExportUtil;

    final CellMapper cellMapper;
    final KpiMapper kpiMapper;
    final UserMapper userMapper;
    final PrbMapper prbMapper;
    final MroDataMapper mroDataMapper;

    public TableService(DataSource dataSource, XlsxInsert xlsxInsert, TableExportUtil tableExportUtil, CellMapper cellMapper, KpiMapper kpiMapper, UserMapper userMapper, PrbMapper prbMapper, MroDataMapper mroDataMapper) {

        this.dataSource = dataSource;
        this.xlsxInsert = xlsxInsert;
        this.tableExportUtil = tableExportUtil;
        this.cellMapper = cellMapper;
        this.kpiMapper = kpiMapper;
        this.userMapper = userMapper;
        this.prbMapper = prbMapper;
        this.mroDataMapper = mroDataMapper;
    }

    @SneakyThrows
    public Resp getTables() {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("show tables;");
        ResultSet resultSet = statement.executeQuery();
        HashMap<String, Map<String, String>> map = new HashMap<>(16);
        while (resultSet.next()) {
            HashMap<String, String> tmp = new HashMap<>(16);
            String table = resultSet.getString(1);
            if (table.contains("user") || table.contains("prb") || table.contains("PRB")) {
                tmp.put("downloadable", "false");
            } else {
                tmp.put("downloadable", "true");
            }
            map.put(table, tmp);
        }
        return new Resp(map);
    }

    @SneakyThrows
    public Resp insertToTable(String tableName, MultipartFile file) {
        String filePath = SAVE_DIR + UUID.randomUUID() + ".xlsx";
        File desFile = new File(filePath);
        FileOutputStream stream = new FileOutputStream(desFile);
        stream.write(file.getBytes());
        stream.flush();
        stream.close();

        Map<String, Integer> insert = xlsxInsert.insert(tableName, filePath);

        FileUtils.forceDelete(desFile);

        return new Resp(insert);
    }

    @SneakyThrows
    public Resp exportTable(String tableName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        String res = ConnectPythonUtil.connect(map, "exportTable");
        return new Resp(res);
    }
}

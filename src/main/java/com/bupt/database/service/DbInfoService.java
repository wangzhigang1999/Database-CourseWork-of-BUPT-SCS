package com.bupt.database.service;

import com.bupt.database.annotation.AuthCheck;
import com.bupt.database.pojo.Resp;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanz
 */
@Component
public class DbInfoService {

    final DataSource dataSource;
    Logger logger = LoggerFactory.getLogger(DbInfoService.class);


    public DbInfoService(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    @AuthCheck(role = "admin")
    public Resp getTimeoutInfo(String key) {
        var dir = "show variables like '%dir%';";
        var time = "show variables like '%timeout%';";
        var buffer = "show variables like '%buffer%';";

        if (key != null) {
            dir = "show variables like '%" + key + "%';";
        }
        HashMap<String, Map<String, String>> map = new HashMap<>(16);
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(dir);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var tmp = new HashMap<String, String>(16);
            tmp.put("current_value", resultSet.getString(2));
            tmp.put("changeable", String.valueOf(resultSet.getString(1).contains("timeout")));
            map.put(resultSet.getString(1), tmp);
        }

        preparedStatement = connection.prepareStatement(buffer);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var tmp = new HashMap<String, String>(16);
            tmp.put("current_value", resultSet.getString(2));
            tmp.put("changeable", String.valueOf(resultSet.getString(1).contains("timeout")));
            map.put(resultSet.getString(1), tmp);
        }

        preparedStatement = connection.prepareStatement(time);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var tmp = new HashMap<String, String>(16);
            tmp.put("current_value", resultSet.getString(2));
            tmp.put("changeable", String.valueOf(resultSet.getString(1).contains("timeout")));
            map.put(resultSet.getString(1), tmp);
        }


        return new Resp(map);
    }

    @SneakyThrows
    @AuthCheck(role = "admin")
    public Resp updateDbInfo(MultiValueMap<String, String> parameters) {
        Connection connection = dataSource.getConnection();
        parameters.forEach((a, b) -> {
            var sql = "set global %s =%s;".formatted(a, b.get(0));
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                logger.error(e.toString() + "  " + sql);
            }
        });

        return new Resp("success");
    }
}

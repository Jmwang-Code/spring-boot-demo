package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;

import java.util.Arrays;
import java.util.List;

public class KingBase8JDBCAdapter extends JDBCAdapter {

    public KingBase8JDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:kingbase8://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.KINGBASE8;
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("kingbase", "security");
    }
}
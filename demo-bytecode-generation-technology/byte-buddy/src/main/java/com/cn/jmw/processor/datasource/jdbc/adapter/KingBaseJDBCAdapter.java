package com.cn.jmw.processor.datasource.jdbc.adapter;//package cn.jt.bds.framework.processor.jdbc.adapter;
//
//import cn.jt.bds.framework.processor.jdbc.JDBCAdapter;
//
//public class KingBaseJDBCAdapter extends JDBCAdapter {
//
//    public KingBaseJDBCAdapter(String hostname, String port, String databaseName, String username, String password) {
//        super(hostname, port, databaseName, username, password);
//    }
//
//    @Override
//    public String getConnectionString() {
//        return "jdbc:kingbase://" + super.hostname + ":" + super.port + "/" + super.databaseName
//                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
//    }
//}
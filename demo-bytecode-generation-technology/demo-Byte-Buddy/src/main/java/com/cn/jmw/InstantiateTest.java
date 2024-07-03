package com.cn.jmw;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.jdbc.adapter.DorisJDBCAdapter;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.sql.SQLException;

public class InstantiateTest {
    public static void main(String[] args)throws IllegalAccessException, InstantiationException, SQLException {
        JDBCConnectionEntity root = new JDBCConnectionEntity(DatabaseEnum.DORIS, "192.168.10.202", 9030, "bds_log", "root", "123456aA!@");

        DorisJDBCAdapter adapter = DatabaseAdapterFactory.getAdapter(root, DorisJDBCAdapter.class);
        Object instantiate = adapter.instantiate("bds_log", "bds_asset_info");
        System.out.println(1);
    }
}

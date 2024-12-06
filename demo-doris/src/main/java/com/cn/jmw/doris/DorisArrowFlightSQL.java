package com.cn.jmw.doris;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.arrow.adbc.core.AdbcConnection;
import org.apache.arrow.adbc.core.AdbcDatabase;
import org.apache.arrow.adbc.core.AdbcDriver;
import org.apache.arrow.adbc.core.AdbcStatement;
import org.apache.arrow.adbc.driver.flightsql.FlightSqlDriver;
import org.apache.arrow.adbc.driver.jdbc.JdbcArrowReader;
import org.apache.arrow.flight.Location;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowReader;
import org.junit.Test;

public class DorisArrowFlightSQL {

    /**
     * <h1>一、前置要求：be和fe分别配置</h1>
     * <br>
     * 比如FE arrow_flight_sql_port = 8815 <br>
     *    BE arrow_flight_sql_port = 8816 <br>
     * <br>
     * <h2>二、idea上配置</h2>
     * <h3>2.1 虚拟机选项上增加这个命令 </h3>
     * --add-opens=java.base/java.nio=ALL-UNNAMED <br>
     * <h3>2.2 环境变量上增加 </h3>
     * JDK_JAVA_OPTIONS="--add-opens=java.base/java.nio=ALL-UNNAMED"<br><br>
     *
     * <h2>三、jar包运行的命令中增加</h2>
     * <h3>3.1 直接JVM虚拟机命令</h3>
     * java --add-opens=java.base/java.nio=ALL-UNNAMED -jar xxx.jar
     * <h3>3.2 环境变量命令</h3>
     * env JDK_JAVA_OPTIONS="--add-reads=org.apache.arrow.flight.core=ALL-UNNAMED --add-opens=java.base/java.nio=org.apache.arrow.memory.core,ALL-UNNAMED" java -jar xxx.jar
     */
    //--add-opens=java.base/java.nio=ALL-UNNAMED
    @Test
    public void ADBC() throws Exception {
        long start = System.currentTimeMillis();
        // 1. new driver
        final BufferAllocator allocator = new RootAllocator();
        FlightSqlDriver driver = new FlightSqlDriver(allocator);
        Map<String, Object> parameters = new HashMap<>();
        AdbcDriver.PARAM_URI.set(parameters, Location.forGrpcInsecure("192.168.10.202", 8815).getUri().toString());
        AdbcDriver.PARAM_USERNAME.set(parameters, "root");
        AdbcDriver.PARAM_PASSWORD.set(parameters, "123456aA!@");
        AdbcDatabase adbcDatabase = driver.open(parameters);

        // 2. new connection
        AdbcConnection connection = adbcDatabase.connect();
        AdbcStatement stmt = connection.createStatement();

        // 3. execute query
        stmt.setSqlQuery("select * from bds_log.bds_wangc_log;");
        AdbcStatement.QueryResult queryResult = stmt.executeQuery();
        ArrowReader reader = queryResult.getReader();

        int i = 0;
        // 4. load result
        List<String> result = new ArrayList<>();
        while (reader.loadNextBatch()) {
            VectorSchemaRoot root = reader.getVectorSchemaRoot();
            List<FieldVector> fieldVectors = root.getFieldVectors();

            // 获取列名
            List<String> columnNames = fieldVectors.stream()
                    .map(vector -> vector.getField().getName())
                    .collect(Collectors.toList());
//            System.out.println("列名: " + columnNames);

            // 遍历行数据
            int rowCount = root.getRowCount();
//            System.out.println("总行数: " + rowCount);

            for (int j = 0; j < rowCount; j++) {
                List<Object> row = new ArrayList<>();
                for (FieldVector fieldVector : fieldVectors) {
                    Object value = fieldVector.getObject(j); // 获取每一列的数据
                    row.add(value);
                }
//                System.out.println("Row " + j + ": " + row);
            }
            String tsvString = root.contentToTSVString();

//            result.add(tsvString);
            if (++i % 1000 == 0) {
                System.out.println(i);
                System.out.println("当前时间:" + (System.currentTimeMillis() - start) / 1000);
            }
        }
        System.out.printf("batchs %d\n", result.size());

        System.out.println("最后时间:" + (System.currentTimeMillis() - start) / 1000);
        // 5. close
        reader.close();
        queryResult.close();
        stmt.close();
        connection.close();
    }
}
package com.cn.jmw.processor.datasource.jdbc.dialect.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuerySQLResult{

    String sql;
    Object[] data;

    public String getRealSql(){
        StringBuilder realSql = new StringBuilder();
        int dataIdx = 0;

        for (int i = 0; i < sql.length(); i++) {
            char ch = sql.charAt(i);
            if (ch == '?' && dataIdx < data.length) {
                Object value = data[dataIdx++];
                if (value instanceof String) {
                    realSql.append(value);
                } else if (value == null) {
                    realSql.append("NULL");
                } else {
                    realSql.append(value);
                }
            } else {
                realSql.append(ch);
            }
        }

        return realSql.toString();
    }
}

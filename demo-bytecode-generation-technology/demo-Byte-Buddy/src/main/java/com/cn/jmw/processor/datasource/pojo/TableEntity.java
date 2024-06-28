package com.cn.jmw.processor.datasource.pojo;

import lombok.Data;

import java.util.List;

@Data
public class TableEntity {
    private String tableName;
    private List<ColumnEntity> columns;

}
package com.cn.jmw.processor.datasource.pojo;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseEntity {
    private DatabaseEnum databaseEnum;
    private String databaseName;
    private List<TableEntity> tables;

}

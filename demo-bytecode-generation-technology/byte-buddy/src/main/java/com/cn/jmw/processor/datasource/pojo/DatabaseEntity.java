package com.cn.jmw.processor.datasource.pojo;

import java.util.List;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DatabaseEntity类用于表示数据库的基本信息。
 * <p>
 * 该类包含数据库名称、数据库类型以及该数据库中的表信息。
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseEntity {
    /**
     * 数据库类型的枚举值。
     */
    private DatabaseEnum databaseEnum;

    /**
     * 数据库名称。
     */
    private String databaseName;

    /**
     * 数据库中的表信息列表。
     *
     * @return List<TableEntity> 表对象的列表
     */
    private List<TableEntity> tables;
}
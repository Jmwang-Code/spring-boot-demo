package com.cn.jmw.processor.datasource.pojo;

import java.util.List;
import lombok.Data;
/**
 * TableEntity类用于表示数据库表的信息。
 * <p>
 * 该类包含表名和与之相关联的列信息列表。
 * </p>
 */
@Data
public class TableEntity {
    /**
     * 表名。
     */
    private String tableName;

    /**
     * 列信息列表，这些列属于该表。
     *
     * @return List<ColumnEntity> 列对象的列表
     */
    private List<ColumnEntity> columns;
}
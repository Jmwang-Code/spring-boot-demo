package com.cn.jmw.demodesignmode.template.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;

/**
 * ORM映射定制化的接口
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs,int rowNum) throws Exception;
}

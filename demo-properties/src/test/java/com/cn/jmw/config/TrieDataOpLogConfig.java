package com.cn.jmw.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "oplog")
public class TrieDataOpLogConfig {

	/**
	 * 缓存数据目录
	 */
	@XmlElement
	private String dir;

	/**
	 * 缓存数据库初始化SQL
	 */
	@XmlElement
	private String initSql;

	/**
	 * 缓存查询用SQL
	 */
	@XmlElement
	private String querySql;

	/**
	 * 缓存增加用SQL
	 */
	@XmlElement
	private String insertSql;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getInitSql() {
		return initSql;
	}

	public void setInitSql(String initSql) {
		this.initSql = initSql;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

}

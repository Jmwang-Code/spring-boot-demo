package com.cn.jmw.config;

import javax.xml.bind.annotation.*;

/**
 * 前缀树缓存配置信息类
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cache")
public class TrieDataCacheConfig {

	/**
	 * 缓存数据目录
	 */
	@XmlElement
	private String dir;

	/**
	 * 是否启用缓存
	 */
	@XmlAttribute
	private boolean enable;

	/**
	 * 缓存数据库初始化SQL
	 */
	@XmlElement
	private String initSql;

	/**
	 * 判断缓存是否存在的SQL
	 */
	@XmlElement
	private String checkExistsSql;

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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getInitSql() {
		return initSql;
	}

	public void setInitSql(String initSql) {
		this.initSql = initSql;
	}

	public String getCheckExistsSql() {
		return checkExistsSql;
	}

	public void setCheckExistsSql(String checkExistsSql) {
		this.checkExistsSql = checkExistsSql;
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

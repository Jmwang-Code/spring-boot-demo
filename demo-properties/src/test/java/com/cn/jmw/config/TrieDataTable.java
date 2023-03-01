package com.cn.jmw.config;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrieDataTable {

	@XmlAttribute(required = true)
	private String id;

	@XmlAttribute
	private boolean enable = true;

	@XmlAttribute
	private String remoteHandler;

	@XmlAttribute(required = true)
	private String name;

	@XmlElement
	private String sql;

	@XmlElement
	private TrieDataESQuery esQuery;

	@XmlElementWrapper(name = "rows")
	@XmlElement(name = "row")
	private List<TrieDataTableRow> rows;

	@XmlAttribute
	private boolean disableCache;

	@XmlAttribute
	private boolean disableRemote;

	@XmlAttribute
	private String mode = "add";

	@XmlAttribute
	private Integer nameMinLength;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getRemoteHandler() {
		return remoteHandler;
	}

	public void setRemoteHandler(String remoteHandler) {
		this.remoteHandler = remoteHandler;
	}

	public List<TrieDataTableRow> getRows() {
		return rows;
	}

	public void setRows(List<TrieDataTableRow> rows) {
		this.rows = rows;
	}

	public boolean isDisableCache() {
		return disableCache;
	}

	public void setDisableCache(boolean disableCache) {
		this.disableCache = disableCache;
	}

	public boolean isDisableRemote() {
		return disableRemote;
	}

	public void setDisableRemote(boolean disableRemote) {
		this.disableRemote = disableRemote;
	}

	public TrieDataESQuery getEsQuery() {
		return esQuery;
	}

	public void setEsQuery(TrieDataESQuery esQuery) {
		this.esQuery = esQuery;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getNameMinLength() {
		return nameMinLength;
	}

	public void setNameMinLength(Integer nameMinLength) {
		this.nameMinLength = nameMinLength;
	}

	@Override
	public String toString() {
		return name + "[" + id + "]";
	}

	public static boolean isAddMode(String mode) {
		return "add".equalsIgnoreCase(mode);
	}

	public static boolean isDeleteMode(String mode) {
		return "delete".equalsIgnoreCase(mode);
	}
}

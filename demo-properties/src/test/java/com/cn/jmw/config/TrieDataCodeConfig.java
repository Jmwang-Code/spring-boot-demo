package com.cn.jmw.config;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrieDataCodeConfig {

	@XmlAttribute(required = true)
	private String id;

	@XmlAttribute
	private String name;

	@XmlAttribute
	private byte type;

	@XmlAttribute
	private String prefix;

	@XmlAttribute
	private boolean enable = true;

	@XmlElementWrapper(name = "tables")
	@XmlElement(name = "table")
	private List<TrieDataTable> tables;
	
	@XmlAttribute
	private String multiCodeMode = "Append";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<TrieDataTable> getTables() {
		return tables;
	}

	public void setTables(List<TrieDataTable> tables) {
		this.tables = tables;
	}

	public String getMultiCodeMode() {
		return multiCodeMode;
	}

	public void setMultiCodeMode(String multiCodeMode) {
		this.multiCodeMode = multiCodeMode;
	}

}

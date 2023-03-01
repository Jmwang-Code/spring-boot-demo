package com.cn.jmw.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrieDataESQuery {

	@XmlAttribute
	private String indexName;

	@XmlAttribute
	private String typeName;

	@XmlAttribute
	private String nameField;

	@XmlAttribute
	private String codeField;

	@XmlValue
	private String esQuery;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getCodeField() {
		return codeField;
	}

	public void setCodeField(String codeField) {
		this.codeField = codeField;
	}

	public String getEsQuery() {
		return esQuery;
	}

	public void setEsQuery(String esQuery) {
		this.esQuery = esQuery;
	}

}

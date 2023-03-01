package com.cn.jmw.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * 前缀树连接配置
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TrieDataConnectionConfig {

	/**
	 * 连接URL
	 */
	@XmlElement
	private String url;

	/**
	 * 用户名
	 */
	@XmlElement
	private String user;

	/**
	 * 密码
	 */
	@XmlElement
	private String password;
	
	@XmlElement
    private String connName;

	@XmlAttribute
	private boolean usePasswordService;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUsePasswordService() {
		return usePasswordService;
	}

	public void setUsePasswordService(boolean usePasswordService) {
		this.usePasswordService = usePasswordService;
	}

    public String getConnName() {
        return connName;
    }

    public void setConnName(String connName) {
        this.connName = connName;
    }
	

}

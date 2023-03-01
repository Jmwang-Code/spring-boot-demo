package com.cn.jmw.config;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrieDataElasticsearchConfig {

	@XmlAttribute
	private long scrollTimeout;

	@XmlAttribute
	private int batchSize;

	@XmlElementWrapper(name = "hosts")
	@XmlElement(name = "host")
	private List<TrieDataElasticsearchHost> hosts;

	@XmlElementWrapper(name = "settings")
	@XmlElement(name = "setting")
	private List<TrieDataElasticsearchSetting> settings;

	public long getScrollTimeout() {
		return scrollTimeout;
	}

	public void setScrollTimeout(long scrollTimeout) {
		this.scrollTimeout = scrollTimeout;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public List<TrieDataElasticsearchHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<TrieDataElasticsearchHost> hosts) {
		this.hosts = hosts;
	}

	public List<TrieDataElasticsearchSetting> getSettings() {
		return settings;
	}

	public void setSettings(List<TrieDataElasticsearchSetting> settings) {
		this.settings = settings;
	}

}

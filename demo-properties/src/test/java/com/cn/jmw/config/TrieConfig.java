package com.cn.jmw.config;

/**
 * @author jmw
 * @Description TODO
 * @date 2022年12月09日 17:08
 * @Version 1.0
 */

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "config")
public class TrieConfig {

    @XmlElement(name = "connection")
    private TrieDataConnectionConfig connection;

    @XmlElementWrapper(name = "codes")
    @XmlElement(name = "code")
    private List<TrieDataCodeConfig> codes;

    @XmlElement(name = "cache")
    private TrieDataCacheConfig cache;

    @XmlElement(name = "oplog")
    private TrieDataOpLogConfig oplogConfig;

    @XmlElement(name = "elasticsearch")
    private TrieDataElasticsearchConfig elasticsearchConfig;

    @XmlAttribute
    private boolean enableTrieCompress;

    @XmlAttribute
    private boolean enableTrieInverted;

    @XmlAttribute
    private boolean enableTrieAllSearch;

    @XmlAttribute
    private boolean disableTrieLoad;

    public TrieDataConnectionConfig getConnection() {
        return connection;
    }

    public void setConnection(TrieDataConnectionConfig connection) {
        this.connection = connection;
    }

    public List<TrieDataCodeConfig> getCodes() {
        return codes;
    }

    public void setCodes(List<TrieDataCodeConfig> codes) {
        this.codes = codes;
    }

    public TrieDataCacheConfig getCache() {
        return cache;
    }

    public void setCache(TrieDataCacheConfig cache) {
        this.cache = cache;
    }

    public boolean isEnableTrieCompress() {
        return enableTrieCompress;
    }

    public void setEnableTrieCompress(boolean enableTrieCompress) {
        this.enableTrieCompress = enableTrieCompress;
    }

    public boolean isEnableTrieInverted() {
        return enableTrieInverted;
    }

    public void setEnableTrieInverted(boolean enableTrieInverted) {
        this.enableTrieInverted = enableTrieInverted;
    }

    public TrieDataElasticsearchConfig getElasticsearchConfig() {
        return elasticsearchConfig;
    }

    public void setElasticsearchConfig(
            TrieDataElasticsearchConfig elasticsearchConfig) {
        this.elasticsearchConfig = elasticsearchConfig;
    }

    public TrieDataOpLogConfig getOplogConfig() {
        return oplogConfig;
    }

    public void setOplogConfig(TrieDataOpLogConfig oplogConfig) {
        this.oplogConfig = oplogConfig;
    }

    public boolean isDisableTrieLoad() {
        return disableTrieLoad;
    }

    public void setDisableTrieLoad(boolean disableTrieLoad) {
        this.disableTrieLoad = disableTrieLoad;
    }

    public boolean isEnableTrieAllSearch() {
        return enableTrieAllSearch;
    }

    public void setEnableTrieAllSearch(boolean enableTrieAllSearch) {
        this.enableTrieAllSearch = enableTrieAllSearch;
    }

}

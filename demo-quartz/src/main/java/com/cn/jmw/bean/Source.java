package com.cn.jmw.bean;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月02日 16:44
 * @Version 1.0
 */
public class Source {

    String sourceId;
    boolean b;

    public Source(String sourceId, boolean b) {
        this.sourceId = sourceId;
        this.b = b;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Source{" +
                "sourceId='" + sourceId + '\'' +
                ", b=" + b +
                '}';
    }
}

package com.cn.jmw.Writable;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 1. Writable 序列化接口
 * 实现Writable方法
 *
 *2. 自定义的bean放在key中传输，MapReduce框中的Shuffle过程要求对key必须能排序。
 * 实现Comparable<>方法
 *
 * 3. 结果显示在文件
 * 重写toString方法
 */
public class Ser implements Writable,Comparable<Ser> {

    public Ser() {
        super();
    }

    public Ser(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public long getDownFlow() {
        return downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    //序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    //反序列化方法
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        upFlow = dataInput.readLong();
        downFlow = dataInput.readLong();
        sumFlow = dataInput.readLong();
    }

    //重写toString方法
    @Override
    public String toString() {
        return "Ser{" +
                "upFlow=" + upFlow +
                ", downFlow=" + downFlow +
                ", sumFlow=" + sumFlow +
                '}';
    }

    //重写compareTo方法
    @Override
    public int compareTo(Ser o) {
        // 倒序排列，从大到小
        return this.sumFlow > o.getSumFlow() ? -1 : 1;
    }

}

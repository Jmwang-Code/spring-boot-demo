package com.cn.jmw.mapreduce.partition;

import com.cn.jmw.mapreduce.Writable.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

import java.io.IOException;

public class FlowDriver {

    @Test
    public void test() throws IOException, ClassNotFoundException, InterruptedException {

        // 1 获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 设置jar
        job.setJarByClass(FlowDriver.class);

        // 3 关联mapper 和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 4 设置mapper 输出的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        // 5 设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //TODO 8 指定自定义分区器
        job.setPartitionerClass(CustomPartitioner.class);
        //TODO 9 同时指定相应数量的ReduceTask
//        job.setNumReduceTasks(5);
        job.setNumReduceTasks(2);

        // 6 设置数据的输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\coding\\deletedemo\\demo-hadoop\\src\\main\\resources\\phone_data.txt"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\coding\\deletedemo\\demo-hadoop\\src\\main\\resources\\partition2\\"));

        // 7 提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

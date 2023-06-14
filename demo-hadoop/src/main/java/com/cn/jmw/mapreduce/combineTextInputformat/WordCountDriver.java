package com.cn.jmw.mapreduce.combineTextInputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

import java.io.IOException;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月14日 11:17
 * @Version 1.0
 *
 * 一个文件有多个block，每个block都会生成一个maptask，如果一个文件有100个block，那么就会生成100个maptask，这样就会产生大量的小文件，这样就会产生大量的maptask，而且每个maptask都要读取文件头，这样就会产生大量的IO操作，这样就会降低效率。
 *
 * CombineTextInputFormat可以将多个小文件合并成一个切片，这样就会减少maptask的数量，减少IO操作，提高效率。
 */
public class WordCountDriver {

    @Test
    public void wordcount() throws IOException, InterruptedException, ClassNotFoundException {


        // 1 获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        // 3 关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4 设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终输出的kV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\coding\\deletedemo\\demo-hadoop\\src\\main\\resources\\combine"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\coding\\deletedemo\\demo-hadoop\\src\\main\\resources\\combineOut2"));

        // 设置inputFormat,默认为TextInputFormat.class
        job.setInputFormatClass(CombineTextInputFormat.class);
        //设置虚拟存储切片最大值4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);


        // 7 提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);

    }

}

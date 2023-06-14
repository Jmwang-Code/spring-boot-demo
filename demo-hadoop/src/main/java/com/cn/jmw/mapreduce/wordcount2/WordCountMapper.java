package com.cn.jmw.mapreduce.wordcount2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月14日 11:17
 * @Version 1.0
 *
 * KEYIN map阶段输入key的类型
 * VALUEIN map阶段输入value的类型
 * KEYOUT map阶段输出key的类型
 * VALUEOUT map阶段输出value的类型
 *
 * 重写Mapper的各个方法，实现自己的业务逻辑
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text word = new Text();
    private IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws java.io.IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String w : words) {
            word.set(w);
            context.write(word, one);
        }
    }
}

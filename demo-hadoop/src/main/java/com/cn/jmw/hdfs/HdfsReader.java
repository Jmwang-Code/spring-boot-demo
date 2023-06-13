package com.cn.jmw.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockStoragePolicySpi;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月12日 15:28
 * @Version 1.0
 */
public class HdfsReader {

    @Test
    public void main() throws IOException {

        // Create a configuration object
        Configuration conf = new Configuration();

        // Set the URI of the HDFS cluster
        conf.set("fs.defaultFS","hdfs://192.168.102.69:8020");

        //user
        // System.setProperty("HADOOP_USER_NAME", "root");

        // Create a file system object
        FileSystem fs = FileSystem.get(conf);

        // Get the home directory of HDFS
        Path homeDir = fs.getHomeDirectory();
        Collection<? extends BlockStoragePolicySpi> allStoragePolicies = fs.getAllStoragePolicies();
        System.out.println("**************"+allStoragePolicies);

        // Print the path of the home directory
        System.out.println("**************"+homeDir);

        // Create a path object for the file to be read
        Path filePath = new Path("/");

        // Open the file for reading
        BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(filePath)));

        // Read the file line by line
        String line;
        while((line =reader.readLine())!=null)
        {
            System.out.println(line);
        }

        // Close the file
        reader.close();
    }
}

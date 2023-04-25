package com.cn.jmw.demodesignmode.template.course;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkCourseTest {
    public static void main(String[] args) {
        log.info("---Java架构师课程---");
        NetworkCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();

        log.info("---大数据课程---");
        NetworkCourse bigDataCourse = new BigDataCourse(true);
        bigDataCourse.createCourse();

    }
}

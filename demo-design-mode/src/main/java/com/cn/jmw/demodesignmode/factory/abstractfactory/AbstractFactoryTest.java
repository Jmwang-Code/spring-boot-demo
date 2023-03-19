package com.cn.jmw.demodesignmode.factory.abstractfactory;

public class AbstractFactoryTest {

    public static void main(String[] args) {
        JavaCourseFactory factory = new JavaCourseFactory();
        factory.createNote().edit();
        factory.createVideo().record();

        CourseFactory pythonCourseFactory = new PythonCourseFactory();
        pythonCourseFactory.createNote().edit();
        pythonCourseFactory.createVideo().record();
    }

}

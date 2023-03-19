package com.cn.jmw.demodesignmode.factory.factorymethod;


import com.cn.jmw.demodesignmode.factory.ICourse;
import com.cn.jmw.demodesignmode.factory.JavaCourse;

/**
 * Created by Tom.
 */
public class JavaCourseFactory implements ICourseFactory {
    public ICourse create() {
        return new JavaCourse();
    }
}

package com.cn.jmw.demodesignmode.factory.factorymethod;

import com.cn.jmw.demodesignmode.factory.ICourse;
import com.cn.jmw.demodesignmode.factory.PythonCourse;

/**
 * Created by Tom.
 */
public class PythonCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new PythonCourse();
    }
}

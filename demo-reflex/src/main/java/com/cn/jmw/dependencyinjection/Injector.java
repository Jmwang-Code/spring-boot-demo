package com.cn.jmw.dependencyinjection;

import java.lang.reflect.Field;

/**
 * 这是一个名为Injector的类。
 * 它包含一个名为inject的静态方法，该方法将实例注入到标有Inject注解的字段中。
 */
public class Injector {
    /**
     * 此方法将实例注入到标有Inject注解的字段中。
     * @param obj 需要注入实例的字段的对象。
     * @throws IllegalAccessException 如果字段不可访问。
     * @throws InstantiationException 如果声明字段的类无法实例化。
     */
    public static void inject(Object obj) throws IllegalAccessException, InstantiationException {
        //获取对象的所有字段（属性）
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            //检查字段是否标有Inject注解
            if (field.isAnnotationPresent(Inject.class)) {
                //设置字段可访问
                field.setAccessible(true);
                //获取字段的类型
                Class<?> fieldType = field.getType();
                //实例化字段的类型. 这里假设字段的类型有一个无参构造函数
                Object instanceToInject = fieldType.newInstance();
                //将实例注入到字段中
                field.set(obj, instanceToInject);
            }
        }
    }
}
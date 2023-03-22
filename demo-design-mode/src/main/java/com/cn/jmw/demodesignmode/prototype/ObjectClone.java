package com.cn.jmw.demodesignmode.prototype;

import com.cn.jmw.demodesignmode.prototype.deep.JinGuBang;
import com.cn.jmw.demodesignmode.prototype.deep.QiTianDaSheng;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * @author jmw
 * @Description 原型克隆类
 * @date 2023年03月22日 17:31
 * @Version 1.0
 * 克隆并非复制
 */
public class ObjectClone<T> implements Serializable, Cloneable {

    private T t;

    public ObjectClone(T t) {
        this.t = t;
    }

    /**
     * 深克隆
     */
    @Override
    protected T clone() throws CloneNotSupportedException {
        T clonedObject = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            clonedObject = (T) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return clonedObject;
    }

    /**
     * 潜克隆
     */
    public T shallowClone() {
        try {
            // 使用反射获取对象的类
            Class<?> aClass = t.getClass();

            // 创建一个新对象
            T o = (T) aClass.getDeclaredConstructor().newInstance();

            // 遍历字段，复制基本类型字段和对其他对象的引用
            Class<?> destinationClass = o.getClass();
            Field[] sourceFields = t.getClass().getDeclaredFields();
            Field[] destinationFields = destinationClass.getDeclaredFields();

            for (Field sourceField : sourceFields) {
                for (Field destinationField : destinationFields) {
                    if (sourceField.getName().equals(destinationField.getName())) {
                        sourceField.setAccessible(true);
                        destinationField.setAccessible(true);
                        destinationField.set(o, sourceField.get(t));
                    }
                }
            }
            return o;
        } catch (ReflectiveOperationException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
        qiTianDaSheng.jinGuBang = new JinGuBang();
        qiTianDaSheng.height = 1;
        qiTianDaSheng.weight = 1;
        qiTianDaSheng.birthday = new Date();
        ObjectClone<QiTianDaSheng> qiTianDaShengObjectClone = new ObjectClone(qiTianDaSheng);

        try {
            QiTianDaSheng clone = qiTianDaShengObjectClone.clone();
            System.out.println(clone.jinGuBang == qiTianDaSheng.jinGuBang);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        QiTianDaSheng shallowClone = qiTianDaShengObjectClone.shallowClone();
        System.out.println(shallowClone.jinGuBang == qiTianDaSheng.jinGuBang);

    }
}

package com.cn.jmw.demodesignmode.factory.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年02月07日 21:01
 * @Version 1.0
 */
public class SimpleFactory {

    public static Optional<? extends Interface> create(Class<? extends Interface> clazz){
        try {
            if (clazz!=null){
                Optional<? extends Interface> anInterface = Optional.of(clazz.getConstructor().newInstance());
                return anInterface;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

package com.cn.jmw.demodesignmode.proxy.dynamic.jdkproxy;


import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/3/10.
 */
public class JDKProxyTest {

    public static void main(String[] args) {
        try {

            Object obj = new JDKMeipo().getInstance(new Girl());
            Method method = obj.getClass().getMethod("findLove",null);
            method.invoke(obj);

            //obj.findLove();

            //$Proxy0
//            byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
//            os.write(bytes);
//            os.close();
            if (obj instanceof Girl){
                Girl girl = (Girl)obj;
            }
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}

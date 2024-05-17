package com.cn.jmw;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DynamicClassGenerator {
    public static void main(String[] args) throws Exception {
        // 创建一个新的类写入器
        ClassWriter cw = new ClassWriter(0);
        // 定义一个公共类，无超类，没有包名
        cw.visit(Opcodes.V1_6, // 类文件版本
                Opcodes.ACC_PUBLIC, // 类修饰符
                "MyClass", // 类名
                null, // signature
                null, // 超类
                null);  // 接口

        // 定义构造函数
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 生成一个返回"Hello, World!"的方法
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "getMessage", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/String", "INSTANCE", "Ljava/lang/String;");
        mv.visitLdcInsn("Hello, World!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "intern", "()Ljava/lang/String;");
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        cw.visitEnd();

        // 将生成的类转换为字节数组
        byte[] data = cw.toByteArray();

        // 使用自定义类加载器加载类
        Class<?> clazz = new MyClassLoader().defineClass("MyClass", data);

        // 调用静态方法获取信息
        Object result = clazz.getMethod("getMessage").invoke(null);
        System.out.println(result);
    }

    // 自定义类加载器，用于加载动态生成的类
    public static class MyClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }
    }
}
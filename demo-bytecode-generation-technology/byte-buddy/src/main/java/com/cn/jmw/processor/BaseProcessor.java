package com.cn.jmw.processor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.MUST_PROVIDE_TYPE_PARAMETER;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 基本处理器抽象类，实现了Processor接口。该类提供了关于输入数据类型的处理能力。
 *
 * @param <T> 输入数据类型
 * @param <R> 输出数据类型
 */
public abstract class BaseProcessor<T, R> implements Processor<T, R> {

    private final Type inputType;  // 输入数据的类型
    private Object[] data;  // 可变参数字段，用于存储额外数据

    /**
     * 默认构造函数，自动获取输入数据的类型。
     * @throws Exception 如果未提供类型参数，将抛出异常
     */
    public BaseProcessor() {
        try {
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            this.inputType = genericSuperclass.getActualTypeArguments()[0]; // 获取类型参数
        } catch (ClassCastException e) {
            throw exception(MUST_PROVIDE_TYPE_PARAMETER); // 抛出类型参数未提供的异常
        }
    }

    /**
     * 带参数的构造函数，可指定输入数据的类型。
     *
     * @param inputType 指定的输入数据类型
     */
    public BaseProcessor(Type inputType) {
        this.inputType = inputType; // 设置输入数据类型
    }

    /**
     * 获取输入数据的类型。
     *
     * @return 输入数据的类型
     */
    public Type getInputType() {
        return inputType; // 返回输入数据类型
    }

    /**
     * 设置额外数据。
     *
     * @param data 可变参数，额外的数据
     */
    public void setData(Object... data) {
        this.data = data; // 设置额外数据
    }

    /**
     * 获取额外数据。
     *
     * @return 额外数据的数组
     */
    public Object[] getData() {
        return data; // 返回额外数据数组
    }

    /**
     * 处理输入数据并返回处理结果，具体实现由子类定义。
     *
     * @param input 输入数据
     * @param data 可变参数，额外的数据
     * @return 处理结果
     * @throws Exception 处理过程中可能抛出的异常
     */
    @Override
    public abstract R process(T input, Object... data) throws Exception;  // 抽象方法，由子类实现
}
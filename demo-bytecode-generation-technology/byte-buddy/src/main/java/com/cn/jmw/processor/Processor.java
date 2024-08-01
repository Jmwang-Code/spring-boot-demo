package com.cn.jmw.processor;

import java.lang.reflect.Type;

/**
 * 责任链中的处理器分为两类：固定的处理器和自定义的处理器。
 * <p>
 * 固定的处理器是你的业务逻辑中必须存在的处理器，例如数据验证处理器、数据转换处理器等。
 * 这些处理器的实现通常是固定的，不需要提供接口供用户自定义。
 * 你可以为每一个固定的处理器创建一个类，这个类实现Processor接口，并提供固定的处理逻辑。
 * </p>
 * <p>
 * 自定义的处理器是可以根据业务需求动态添加的处理器，例如数据丰富处理器、数据过滤处理器等。
 * 这些处理器的实现需要提供接口供用户自定义。
 * 你可以为每一个自定义的处理器创建一个接口，这个接口继承Processor接口。
 * </p>
 *
 * @param <T> 输入数据类型
 * @param <R> 输出数据类型
 */
public interface Processor<T, R> {
    /**
     * 处理输入数据并返回处理结果。
     *
     * @param input 输入数据
     * @param data 可变参数，额外的数据
     * @return 处理结果
     * @throws Exception 处理过程中可能抛出的异常
     */
    R process(T input, Object... data) throws Exception;

    /**
     * 获取输入数据的类型。
     *
     * @return 输入数据的类型
     */
    Type getInputType();

    /**
     * 获取额外数据。
     *
     * @return 额外数据的数组
     */
    Object[] getData();

    /**
     * 设置额外数据。
     *
     * @param data 可变参数，额外的数据
     */
    void setData(Object... data);
}
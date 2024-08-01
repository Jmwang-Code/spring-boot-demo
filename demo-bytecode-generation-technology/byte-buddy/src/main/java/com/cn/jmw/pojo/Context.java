package com.cn.jmw.pojo;

import lombok.Data;

/**
 * Context类用于表示处理上下文。
 * <p>
 * 此类封装了输入和输出的泛型，提供了构造函数以便于创建上下文实例。
 * </p>
 *
 * @param <T> 输入类型
 * @param <R> 输出类型
 */
@Data
public class Context<T, R> {
    // 输入数据
    private T input;
    // 输出数据
    private R output;

    /**
     * 带参数的构造函数，用于创建一个包含输入的Context实例。
     *
     * @param input 输入数据
     */
    public Context(T input){
        this.input = input;
    }

    /**
     * 默认构造函数，用于创建一个空的Context实例。
     */
    public Context(){}
}
package com.cn.jmw.processor;

import java.lang.reflect.Type;

/**
 * 责任链中的处理器分为两类：固定的处理器和自定义的处理器。固定的处理器是你的业务逻辑中必须存在的处理器，而自定义的处理器是可以根据业务需求动态添加的处理器。
 * <p>
 * 固定的处理器通常是你的业务逻辑中必须存在的处理器，例如数据验证处理器、数据转换处理器等。这些处理器的实现通常是固定的，不需要提供接口供用户自定义。你可以为每一个固定的处理器创建一个类，这个类实现Processor接口，并提供固定的处理逻辑。
 * <p>
 * 自定义的处理器是可以根据业务需求动态添加的处理器，例如数据丰富处理器、数据过滤处理器等。这些处理器的实现需要提供接口供用户自定义。你可以为每一个自定义的处理器创建一个接口，这个接口继承Processor接口。
 */
public interface Processor<T, R> {
    R process(T input, Object... data) throws Exception;

    Type getInputType();

    Object getData();

    void setData(Object data);
}
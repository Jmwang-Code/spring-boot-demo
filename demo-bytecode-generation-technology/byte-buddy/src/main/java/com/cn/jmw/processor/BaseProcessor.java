package com.cn.jmw.processor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.MUST_PROVIDE_TYPE_PARAMETER;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

// 实现基本处理器
public abstract class BaseProcessor<T, R> implements Processor<T, R> {

    private final Type inputType;

    private Object data;  // 添加data字段

    public BaseProcessor() {
        try {
            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            this.inputType = genericSuperclass.getActualTypeArguments()[0];
        }catch (ClassCastException e){
            throw exception(MUST_PROVIDE_TYPE_PARAMETER);
        }
    }

    public BaseProcessor(Type inputType) {
        this.inputType = inputType;
    }
//
//    @Override
//    public abstract R process(T input) throws Exception;

    public Type getInputType() {
        return inputType;
    }

//    @Override
    public void setData(Object data) {  // 实现setData方法
        this.data = data;
    }

    public Object getData() {  // 添加getData方法
        return data;
    }

    @Override
    public abstract R process(T input, Object... data) throws Exception;  // 修改process方法

}
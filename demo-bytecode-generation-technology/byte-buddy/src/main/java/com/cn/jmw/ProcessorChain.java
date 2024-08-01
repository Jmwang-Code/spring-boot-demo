package com.cn.jmw;

import com.cn.jmw.common.util.bean.BeanUtils;
import com.cn.jmw.pojo.Context;
import com.cn.jmw.processor.Processor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.*;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.*;

/**
 * ProcessorChain类用于管理和执行处理器链。
 * <p>
 * 此类封装了多个处理器，通过输入上下文逐个处理输入数据，并将结果存储在上下文中。
 * </p>
 */
public class ProcessorChain {
    // 处理器列表
    private List<Processor> processors;

    private ProcessorChain() {
        processors = new ArrayList<>();
    }

    /**
     * 向处理器链添加一个处理器。
     *
     * @param processor 要添加的处理器
     */
    private void addProcessor(Processor processor) {
        processors.add(processor);
    }

    /**
     * 执行处理器链，对输入上下文进行处理。
     *
     * @param context 输入上下文
     * @throws Exception 异常抛出
     */
    public void execute(Context context) throws Exception {
        Object input = context.getInput();
        // 检查输入是否为空
        if (input == null) {
            throw exception(INPUT_NOT_ALLOWED_EMPTY);
        }
        for (Processor processor : processors) {
            // 验证输入类型
            if (input == null && processor.getInputType() != Void.class) {
                throw exception(INPUT_TYPE_NOT_MATCH);
            }
            if (input != null) {
                Type inputType = processor.getInputType();
                // 检查输入类型是否是参数化类型
                if (inputType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) inputType;
                    Type rawType = parameterizedType.getRawType();
                    // 检查原始类型是否是List
                    if (!(rawType instanceof Class) || !List.class.isAssignableFrom((Class<?>) rawType)) {
                        throw exception(INPUT_TYPE_NOT_MATCH);
                    }
                    // 检查实际类型参数
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length != 1 || !(actualTypeArguments[0] instanceof Class)) {
                        throw exception(INPUT_TYPE_NOT_MATCH);
                    }
                    // 检查输入的泛型类型是否匹配
                    if (input instanceof List && !((List<?>) input).isEmpty()) {
                        Object firstItem = ((List<?>) input).get(0);
                        if (!firstItem.getClass().isAssignableFrom((Class<?>) actualTypeArguments[0])) {
                            throw exception(INPUT_TYPE_NOT_MATCH);
                        }
                    }
                } else if (!(inputType instanceof Class) || !input.getClass().isAssignableFrom((Class<?>) inputType)) {
                    throw exception(INPUT_TYPE_NOT_MATCH);
                }
            }
            // 处理输入数据
            input = processor.process(input, processor.getData());
        }
        // 设置处理结果
        context.setOutput(input);
    }

    /**
     * Builder类用于构建ProcessorChain实例。
     */
    public static class Builder {
        private ProcessorChain chain;

        public Builder() {
            chain = new ProcessorChain();
        }

        /**
         * 向处理器链添加处理器。
         *
         * @param processorClass 处理器的类
         * @param data 附加数据
         * @return Builder对象
         */
        public Builder addProcessor(Class<? extends Processor> processorClass, Object... data) {
            Processor processor;
            try {
                processor = BeanUtils.getBean(processorClass);
            } catch (Exception e) {
                try {
                    processor = processorClass.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw exception(UNABLE_TO_CREATE_PROCESSOR_INSTANCE);
                }
            }
            processor.setData(data);
            chain.addProcessor(processor);
            return this;
        }

        /**
         * 向处理器链添加处理器（没有附加数据）。
         *
         * @param processorClass 处理器的类
         * @return Builder对象
         */
        public Builder addProcessor(Class<? extends Processor> processorClass) {
            addProcessor(processorClass, null);
            return this;
        }

        /**
         * 向处理器链添加Lambda处理器。
         *
         * @param inputType 输入类型
         * @param function Lambda处理函数
         * @param <T> 泛型输入类型
         * @param <R> 泛型输出类型
         * @return Builder对象
         */
        public <T, R> Builder addProcessorLambda(Class<T> inputType, Function<T, R> function) {
            return addProcessorLambda(inputType, function, null);
        }

        /**
         * 向处理器链添加带附加数据的Lambda处理器。
         *
         * @param inputType 输入类型
         * @param function Lambda处理函数
         * @param data 附加数据
         * @param <T> 泛型输入类型
         * @param <R> 泛型输出类型
         * @return Builder对象
         */
        public <T, R> Builder addProcessorLambda(Class<T> inputType, Function<T, R> function, Object data) {
            chain.addProcessor(new Processor<T, R>() {
                private Object[] data;  // 添加data字段

                {
                    this.data = data; // 设置data字段的值
                }

                @Override
                public R process(T input, Object... data) throws Exception {
                    return function.apply(input); // 使用function来处理输入
                }

                @Override
                public Class<T> getInputType() {
                    return inputType;
                }

                @Override
                public Object[] getData() {
                    return this.data; // 返回data字段的值
                }

                @Override
                public void setData(Object... data) {
                    this.data = data; // 设置data字段的值
                }
            });
            return this;
        }

        /**
         * 构建ProcessorChain实例。
         *
         * @return ProcessorChain实例
         */
        public ProcessorChain build() {
            return chain;
        }
    }
}
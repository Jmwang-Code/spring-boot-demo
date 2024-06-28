import common.util.bean.BeanUtils;
import pojo.Context;
import processor.Processor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static common.exception.enums.StructuredErrorCodeConstants.*;
import static common.exception.util.ServiceExceptionUtil.*;


// 更新责任链执行逻辑
public class ProcessorChain {

    private List<Processor> processors;

    private ProcessorChain() {
        processors = new ArrayList<>();
    }

    private void addProcessor(Processor processor) {
        processors.add(processor);
    }

    public void execute(Context context) throws Exception {
        Object input = context.getInput();
        if (input == null) {
            throw exception(INPUT_NOT_ALLOWED_EMPTY);
        }
        for (Processor processor : processors) {
            if (input == null && processor.getInputType() != Void.class) {
                throw exception(INPUT_TYPE_NOT_MATCH);
            }
            if (input != null) {
                Type inputType = processor.getInputType();
                if (inputType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) inputType;
                    Type rawType = parameterizedType.getRawType();
                    if (!(rawType instanceof Class) || !List.class.isAssignableFrom((Class<?>) rawType)) {
                        throw exception(INPUT_TYPE_NOT_MATCH);
                    }
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length != 1 || !(actualTypeArguments[0] instanceof Class)) {
                        throw exception(INPUT_TYPE_NOT_MATCH);
                    }
                    // Check if the input's generic type matches the expected generic type
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
            input = processor.process(input, processor.getData());
        }
        context.setOutput(input);
    }

    public static class Builder {
        private ProcessorChain chain;

        public Builder() {
            chain = new ProcessorChain();
        }

        public Builder addProcessor(Class<? extends Processor> processorClass, Object data) {
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

        public Builder addProcessor(Class<? extends Processor> processorClass) {
            addProcessor(processorClass, null);
            return this;
        }

        public <T, R> Builder addProcessorLambda(Class<T> inputType, Function<T, R> function) {
            addProcessorLambda(inputType, function, null);
            return this;
        }

        public <T, R> Builder addProcessorLambda(Class<T> inputType, Function<T, R> function, Object data) {
            chain.addProcessor(new Processor<T, R>() {
                private Object data;  // 添加data字段

                // 添加一个初始化块，用来设置data字段的值
                {
                    this.data = data;
                }

                @Override
                public R process(T input, Object... data) throws Exception {
                    return function.apply(input);  // 使用function来处理输入
                }

                @Override
                public Class<T> getInputType() {
                    return inputType;
                }

                @Override
                public Object getData() {
                    return this.data;  // 返回data字段的值
                }

                @Override
                public void setData(Object data) {
                    this.data = data;  // 设置data字段的值
                }

            });
            return this;
        }

        public ProcessorChain build() {
            return chain;
        }
    }

}
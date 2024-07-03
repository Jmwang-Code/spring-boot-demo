package com.cn.jmw;

import com.cn.jmw.pojo.Context;
import com.cn.jmw.processor.base.Error608Processor;
import com.cn.jmw.processor.base.NumericalCalculationProcessor;
import com.cn.jmw.processor.base.NumericalToBusinessString;
import com.cn.jmw.processor.base.RegularProcessor;
import com.cn.jmw.processor.datasource.DatabaseAdapter;
import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.jdbc.adapter.DorisJDBCAdapter;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;
import org.junit.Test;

import java.sql.SQLException;

public class TestA {

    //单长链条测试
    @Test
    public void singleLongChainTest() throws Exception {
        // 创建责任链
        ProcessorChain chain = new ProcessorChain.Builder()
                .addProcessor(NumericalCalculationProcessor.class) //输入
                .addProcessor(NumericalCalculationProcessor.class) //
                .addProcessorLambda(Integer.class,input -> (Integer)input + 1) //业务
                .addProcessorLambda(Integer.class,input -> (Integer)input * 2) //输出
                .addProcessor(NumericalToBusinessString.class)
                .addProcessorLambda(String.class,input -> (String)""+input) //业务
                .addProcessor(RegularProcessor.class)
                .build();

        // 创建处理上下文
        Context<Integer, String> context = new Context<>(1);

        // 执行责任链
        chain.execute(context);

        // 获取处理结果
        String output = context.getOutput();
        Integer input = context.getInput();
        System.out.println(input);
        System.out.println(output);
    }

    //继承 processor.BaseProcessor 时必须提供类型参数测试
    @Test
    public void error608Test() throws Exception {
        ProcessorChain build = new ProcessorChain.Builder()
                .addProcessor(Error608Processor.class)
                .build();

        Context<Integer, String> context = new Context<>(1);
        build.execute(context);
    }

    //输入类型与处理器的输入类型不匹配
    @Test
    public void error607Test() throws Exception {
        ProcessorChain chain = new ProcessorChain.Builder()
                .addProcessor(NumericalCalculationProcessor.class)
                .addProcessor(RegularProcessor.class)
                .build();

        Context<Integer, String> context = new Context<>(1);

        chain.execute(context);
    }

    //继承 Context<?,?> 时必须提供类型参数测试
    @Test
    public void error609Test() throws Exception {
        ProcessorChain chain = new ProcessorChain.Builder()
                .addProcessor(NumericalCalculationProcessor.class)
                .addProcessor(RegularProcessor.class)
                .build();
        Context<Integer, String> voidVoidContext = new Context<>(null);
        chain.execute(voidVoidContext);
    }

}
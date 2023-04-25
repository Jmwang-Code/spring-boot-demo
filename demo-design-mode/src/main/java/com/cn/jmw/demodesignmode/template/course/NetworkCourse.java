package com.cn.jmw.demodesignmode.template.course;

import lombok.extern.slf4j.Slf4j;

/**
 * 模板会有一个或者多个未现实方法，
 * 而且这几个未实现方法有固定的执行循序
 */
@Slf4j
public abstract class NetworkCourse {

    protected final void createCourse(){
        //1、发布预习资料
        this.postPreResource();

        //2、制作PPT课件
        this.createPPT();

        //3、在线直播
        this.liveVideo();

        //4、提交课件、课堂笔记
        this.postNote();

        //5、提交源码
        this.postSource();

        //6、布置作业，有些课是没有作业，有些课是有作业的
        //如果有作业的话，检查作业，如果没作业，完成了
        if(needHomework()){
            checkHomework();
        }
    }

    abstract void checkHomework();

    //钩子方法：实现流程的微调
    protected boolean needHomework(){return false;}

    final void postSource(){
        log.info("提交源代码");
    }

    final void postNote(){
        log.info("提交课件和笔记");
    }

    final void liveVideo(){
        log.info("直播授课");
    }

    final void createPPT(){
        log.info("创建备课PPT");
    }

    final void postPreResource(){
        log.info("分发预习资料");
    }

}

package com.cn.jmw.aspect;
import cn.hutool.core.bean.BeanUtil;
import com.cn.jmw.aspect.LogModuleHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.util.*;

@Slf4j
public enum ClueLogEnum implements LogModuleHandler {


    /**
     * 初筛线索_待办线索、待办线索_拆分、待办线索_合并、待办线索_分流、
     * 待办线索_审批通过、待办线索_审批驳回、待办线索_目标接收、待办线索_目标拒绝、
     * 待办线索_分配、待办线索_分配通过、待办线索_分配驳回、待处理线索_办结
     */

    /**
     * 初始化
     */
    INIT {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {
            init();
        }
    },


    /**
     * 初筛线索_待办线索
     */
    CLUE_SCREENING_TO_DO_CLUE("初筛线索转待办线索") {//, XSParam.class

        @Override
        public void handle(JoinPoint joinPoint, Object request) {
        }
    },

    /**
     * 待办线索_拆分
     */
    TO_DO_CLUE_SPLIT("待办线索_拆分") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {
        }
    },

    /**
     * 待办线索_合并
     *
     * 原操作日志数据查询
     *
     */
    TO_DO_CLUE_MERGE("待办线索_合并") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {
        }
    },

    /**
     * 待办线索_分流_申请
     */
    TO_DO_CLUE_FLOW("待办线索_分流_申请") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }

    },

    /**
     * 待办线索_分流_审批通过
     *
     * 待办线索_分流_目标接收
     */
    TO_DO_CLUE_APPROVE_PASS("待办线索_分流_审批通过") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    },

    /**
     * 待办线索_分流_审批驳回
     *
     * 待办线索_分流_目标拒绝
     */
    TO_DO_CLUE_APPROVE_REJECT("待办线索_分流_审批驳回") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    },

    /**
     * 待办线索_分配
     */
    TO_DO_CLUE_DISTRIBUTE("待办线索_分配") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    },

    /**
     * 待办线索_分配通过
     */
    TO_DO_CLUE_DISTRIBUTE_PASS("待办线索_分配通过") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    },

    /**
     * 待办线索_分配驳回
     */
    TO_DO_CLUE_DISTRIBUTE_REJECT("待办线索_分配驳回") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    },

    /**
     * 待处理线索_办结
     */
    TO_DO_CLUE_FINISH("待处理线索_办结") {
        @Override
        public void handle(JoinPoint joinPoint, Object request) {

        }
    };

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 入参类型
     */
    private Class<?> paramType;

    /**
     * 返回值
     */
//    private ResponseResult result;
//
//
//    private YJXXService yjxxService;
//
//    private DBXSService dbxsService;
//
//    private DbxsOperaterLogService dbxsOperaterLogService;


    ClueLogEnum() {
    }

    ClueLogEnum(String nodeName) {
        this.nodeName = nodeName;
    }

//    ClueLogEnum(String nodeName, Object param) {
//        this.nodeName = nodeName;
//        this.paramType = param.getClass();
//        this.result = result;
//    }

//    public YJXXService getYjxxService() {
//        return yjxxService;
//    }
//
//    public DBXSService getDbxsService() {
//        return dbxsService;
//    }
//
//    public DbxsOperaterLogService getDbxsOperaterLogService() {
//        return dbxsOperaterLogService;
//    }

    public String getNodeName() {
        return nodeName;
    }

    //初始化
    public void init() {
//        yjxxService = SpringBeanUtil.getBean(YJXXService.class);
//        dbxsService = SpringBeanUtil.getBean(DBXSService.class);
//        dbxsOperaterLogService = SpringBeanUtil.getBean(DbxsOperaterLogService.class);
    }
}

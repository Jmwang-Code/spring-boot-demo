package com.cn.jmw.common.exception.enums;

import com.cn.jmw.common.exception.ErrorCode;

public interface StructuredErrorCodeConstants {

    // ========== 业务结构化错误段 ==========
    // 600-699 业务异常编码保留
    /**
     * 600 - 输入数据不正确 - 一般是参数校验失败
     * 601 - 输出数据不正确 - 一般是返回数据校验失败
     * 602 - 资源未找到 - 一般是查询数据不存在
     * 603 - 操作不允许 - 一般是权限校验失败
     * 604 - 计算错误 - 一般是计算逻辑错误
     * 605 - 连接错误 - 一般是算子之间的连接配置错误
     * 606 - 违反业务规则 - 一般是业务规则校验失败
     * 607 - 输入类型与处理器的输入类型不匹配 - 一般是责任链执行逻辑错误
     * 608 - 继承 processor.BaseProcessor 时必须提供类型参数 - 一般是责任链执行逻辑错误
     * 609 - 无法创建处理器实例 - 一般是责任链执行逻辑错误
     * 610 - 继承 Context<?,?> 时必须提供类型参数 - 一般是责任链执行逻辑错误
     * 611 - 未实现接口中的方法 - 适配器未实现接口中的方法
     * 612 - 数据源元数据为空,可能是元数据获取失败或无元数据 - 一般是数据源元数据获取失败
     * 613 - 无法检索数据库版本 - 一般是数据库版本获取失败
     * 614 - 无法检索数据库元数据,元数据获取失败 - 一般是数据库元数据获取失败
     * 615 - 不支持的数据库类型 - 一般是数据库类型错误
     * 616 - 数据库传入类型为空 - 一般是数据库传入类型为空
     * 617 - 入参input不允许为空 - 一般是入参校验失败
     * 618 - input 为 null - 一般是入参校验失败
     * 619 - input.getDbType（） 为 null - 一般是入参校验失败
     * 620 - 无法连接数据库。（请检查数据库连接信息是否正确） - 一般是数据库连接失败
     */
    ErrorCode INVALID_INPUT = new ErrorCode(600, "输入数据不正确");
    ErrorCode INVALID_OUTPUT = new ErrorCode(601, "输出数据不正确");
    ErrorCode RESOURCE_NOT_FOUND = new ErrorCode(602, "资源未找到");
    ErrorCode OPERATION_NOT_ALLOWED = new ErrorCode(603, "操作不允许");
    ErrorCode CALCULATION_ERROR = new ErrorCode(604, "计算错误");
    ErrorCode CONNECTION_ERROR = new ErrorCode(605, "连接错误");
    ErrorCode BUSINESS_RULE_VIOLATION = new ErrorCode(606, "违反业务规则");
    ErrorCode INPUT_TYPE_NOT_MATCH = new ErrorCode(607, "输入类型与处理器的输入类型不匹配");
    ErrorCode MUST_PROVIDE_TYPE_PARAMETER = new ErrorCode(608, "继承 processor.BaseProcessor<?,?> 时必须提供类型参数");
    ErrorCode UNABLE_TO_CREATE_PROCESSOR_INSTANCE = new ErrorCode(609, "无法创建处理器实例");
    ErrorCode MUST_PROVIDE_TYPE_PARAMETER_CONTEXT = new ErrorCode(610, "继承 Context<?,?> 时必须提供类型参数");
    ErrorCode NOT_IMPLEMENTED_METHOD = new ErrorCode(611, "未实现接口中的方法");
    ErrorCode EMPTY_DATA_SOURCE_METADATA = new ErrorCode(612, "数据源元数据为空,可能是元数据获取失败或无元数据");
    ErrorCode UNABLE_TO_RETRIEVE_DATABASE_VERSION = new ErrorCode(613, "无法检索数据库版本");
    ErrorCode UNABLE_TO_RETRIEVE_DATABASE_METADATA = new ErrorCode(614, "无法检索数据库元数据,元数据获取失败");
    ErrorCode UNSUPPORTED_DATABASE_TYPE = new ErrorCode(615, "不支持的数据库类型");
    ErrorCode DATABASE_INPUT_TYPE_ERROR = new ErrorCode(616, "数据库传入类型为空");
    ErrorCode INPUT_NOT_ALLOWED_EMPTY = new ErrorCode(617, "入参input不允许为空");
    ErrorCode INPUT_IS_NULL = new ErrorCode(618, "input 为 null");
    ErrorCode INPUT_GET_DB_TYPE_IS_NULL = new ErrorCode(619, "input.getDbType（） 为 null");
    ErrorCode UNABLE_TO_CONNECT_DATABASE = new ErrorCode(620, "无法连接数据库。（请检查数据库连接信息是否正确）");

}

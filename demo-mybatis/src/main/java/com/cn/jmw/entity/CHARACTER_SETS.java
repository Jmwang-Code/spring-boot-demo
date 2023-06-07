package com.cn.jmw.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.jmw.handler.CHARACTER_SETS_Handler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月07日 16:40
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "CHARACTER_SETS", autoResultMap = true)
@NoArgsConstructor
public class CHARACTER_SETS implements Serializable {

    //通过mybatis-plus注解写出对应字段
    private static final long serialVersionUID = 1L;

    @TableField(typeHandler = CHARACTER_SETS_Handler.class)
    private String CHARACTER_SET_NAME;

    private String DEFAULT_COLLATE_NAME;

    private String DESCRIPTION;

    private Long MAXLEN;

}

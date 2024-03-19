package com.epam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 监督检查用语具体内容，跟检查单挂钩
 * @Author: nbacheng
 * @Date: 2023-09-11
 * @Version: V1.0
 */
@ApiModel(value = "ics_check_rule_content对象", description = "监督检查用语具体内容，跟检查单挂钩")
@Data
@TableName("ics_check_rule_content")
public class IcsCheckRuleContent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 检查类型
     */
    @ApiModelProperty(value = "检查类型")
    private String checkType;
    /**
     * 检查项目
     */
    @ApiModelProperty(value = "检查项目")
    private String checkProject;
    /**
     * 存在问题
     */
    @ApiModelProperty(value = "存在问题")
    private String existProblem;
    /**
     * 依据法规
     */
    @ApiModelProperty(value = "依据法规")
    private String according;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String category;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 适用对象
     */
    @ApiModelProperty(value = "适用对象")
    private String applicableObject;

    @ApiModelProperty(value = "首项标识（1是、0否）")
    private Boolean isFirst;
}


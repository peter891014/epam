package com.epam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.jeecg.common.aspect.annotation.Dict;
//import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 移动巡检
 * @Author: nbacheng
 * @Date:   2023-07-28
 * @Version: V1.0
 */
@ApiModel(value="监督检查用语", description="监督检查用语")
@Data
public class IcsCheckRule implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**安监备案号*/

    @ApiModelProperty(value = "检查类型")
    private String checkType;
	/**项目名称*/
    @ApiModelProperty(value = "检查项目")
    private String checkProject;
	/**项目类型*/

    @ApiModelProperty(value = "存在问题")
    private String existProblem ;

    @TableField(exist = false)
    @ApiModelProperty(value = "问题详情")
    private IcsCheckRuleDetail detail;

    @ApiModelProperty(value = "依据法规")
    private String according;

    @ApiModelProperty(value = "类别")
    private String category;

	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;

    /**适用对象*/
    @ApiModelProperty(value = "适用对象")
    private String   applicableObject;

}

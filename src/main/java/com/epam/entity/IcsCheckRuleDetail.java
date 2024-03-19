package com.epam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;


/**
 * @Description: 移动巡查详情
 * @Author: nbacheng
 * @Date:   2023-07-28
 * @Version: V1.0
 */
@ApiModel(value="监督检查用于问题细节", description="移动巡查详情")
@Data
public class IcsCheckRuleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "主表id")
    private Integer checkRuleId;

    private String name;


}

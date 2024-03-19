package com.epam.aliyun;

import lombok.Data;

@Data
//@ApiModel("签名信息实体")
public class PolicyVo {

//    @ApiModelProperty("AK")
    private String accessId;

//    @ApiModelProperty("用户表单上传的策略（Policy）")
    private String policy;

//    @ApiModelProperty("签名")
    private String signature;

//    @ApiModelProperty("上传文件时指定的前缀")
    private String dir;

//    @ApiModelProperty("oss保存文件的host")
    private String host;

//    @ApiModelProperty("过期时间")
    private String expire;

}

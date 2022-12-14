package com.epam.secKill.contants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  ProductStatusEnum {

    //枚举值表
    UP(0,"在架"),     //枚举元素
    DOWN(1,"下架");   //枚举元素

    private Integer code;
    private String message;

}

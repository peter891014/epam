package com.epam.excel;/*
 *@title FiledTypeTranslate
 *@description
 *@author ASUS
 *@create 2023/12/6 9:14
 */

import java.math.BigDecimal;

public enum FiledTypeTranslate {
    SINGEL_TEXT("单行文本","varchar(200)"),
    SUMMARY("汇总","int(10)"),
    FIELD_QUOTE("字段引用","varchar(32)"),
    DATETIME("日期时间","datetime(0)"),
    SINGLE_LIST("单选列表","varchar(4)"),
    FLOAT_NUMBER("浮点数","decimal(8,4)"),
    DATE("日期","date"),
    SLAVE_MASTER("从主关联","varchar(32)"),
    PICTURE("图片","varchar(200)"),
    FILE("附件","varchar(200)"),
    BOOLEAN("布尔","tinyint(1)"),
    LONG_TEXT("多行文本","varchar(1000)"),
    MAP("地图","varchar(200)"),
    MUMBER("整数","int(10)"),
    RICH_TEXT("富文本","varchar(2000)"),
    FORMULA_NUMBER("公式-浮点数","decimal(8,2)"),
    PHONE("手机号码","varchar(11)");
    private  String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    FiledTypeTranslate(String name, String code) {
        this.name = name;
        this.code = code;
    }
}

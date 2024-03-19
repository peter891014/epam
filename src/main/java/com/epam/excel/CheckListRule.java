package com.epam.excel;


import com.epam.secKill.contants.ProductStatusEnum;
import com.epam.secKill.utill.EnumUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Data
public class CheckListRule {

    @Id
    private int id;
    /**
     * 产品名
     */
    private String checkType;
    /**
     * 单价
     */
    private String checkProject;
    /**
     * 库存
     */
    private String existProblem;
    /**
     * 产品描述
     */
    private String according;
    /**
     * 小图
     */
    private String category;

}

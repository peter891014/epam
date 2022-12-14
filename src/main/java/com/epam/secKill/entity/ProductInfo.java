package com.epam.secKill.entity;




import com.epam.secKill.contants.ProductStatusEnum;
import com.epam.secKill.utill.Date2LongSerializer;
import com.epam.secKill.utill.EnumUtil;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;
    /**
     * 产品名
     */
    private String productName;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 库存
     */
    private Integer productStock;
    /**
     * 产品描述
     */
    private String productDescription;
    /**
     * 小图
     */
    private String productIcon;
    /**
     * 商品状态 0正常 1下架
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /**
     * 类目编号
     */
    private Integer categoryType;

    /** 创建日期*/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /**更新时间 */
   // @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

   // @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        return (ProductStatusEnum) EnumUtil.getBycode(productStatus,ProductStatusEnum.class);
    }

    public ProductInfo(String productId) {
        this.productId = productId;
        this.productPrice = new BigDecimal(3.2);
    }

    public ProductInfo() {
    }


}

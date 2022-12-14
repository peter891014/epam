package com.epam.secKill.utill;

import com.epam.secKill.entity.ProductInfo;
import com.epam.secKill.entity.SecOrder;

import java.util.Random;

public class SecUtils {
    
    /*
    创建虚拟订单
     */
    public  static SecOrder createDummyOrder(ProductInfo productInfo){
        String key = KeyUtil.getUniqueKey();
        SecOrder secOrder = new SecOrder();
        secOrder.setId(key);
        secOrder.setUserId("userId="+key);
        secOrder.setProductId(productInfo.getProductId());
        secOrder.setProductPrice(productInfo.getProductPrice());
        secOrder.setAmount(productInfo.getProductPrice());
        return secOrder;
    }
    
   /*
   伪支付
    */
    public static  boolean dummyPay(){
        Random random = new Random();
        int result = random.nextInt(1000) % 2;
        if (result == 0){
            return true;
        }
        return false;
    }
}

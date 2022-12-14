package com.epam.DesignPattern.TemplateMethod;

import org.apache.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 抽象类定义骨架流程（查询商户信息，加签，http请求，验签）
 *
 * @author ASUS
 */

public abstract class AbstractMerchantService {

    //查询商户信息
   public abstract void queryMerchantInfo() ;

    //加签
    abstract void signature() ;

    //http 请求
    abstract void httpRequest();

    // 验签
    abstract void verifySinature();

    //模板方法流程
    public ResponseEntity handlerTempPlate(HttpRequest request) {
        //查询商户信息
        queryMerchantInfo();
        //加签
        signature();
        //http 请求
        if(this.isRequestByProxy()) {
            httpRequest();
        }

        // 验签
        verifySinature();
        System.out.println("父类方法完成");
        return new ResponseEntity(HttpStatus.OK);
    }

    // Http是否走代理（提供给子类实现）
    public abstract boolean isRequestByProxy();

}

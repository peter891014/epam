package com.epam.DesignPattern.TemplateMethod;

import org.apache.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @program: epam
 * @description:
 * @author: 作者名字
 * @create: 2022-05-30 21:47
 **/
@Component
public class CompanyBServiceImpl extends AbstractMerchantService {

    public ResponseEntity handier(HttpRequest request) {
        return handlerTempPlate(request);
    }


    @Override
    public void httpRequest() {
        System.out.println("http 请求发送");
    }
    @Override
    public void queryMerchantInfo() {
        System.out.println("查询商户信息");
    }

    //加签
    @Override
    public void signature() {
        System.out.println("加签");
    }


    // 验签
    @Override
    public void verifySinature() {
        System.out.println("验签");
    }


    //公司B是不走代理的
    @Override
    public boolean isRequestByProxy() {
        return false;
    }
}

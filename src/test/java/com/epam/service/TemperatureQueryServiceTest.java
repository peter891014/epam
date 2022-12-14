package com.epam.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.epam.exception.ParamsException;
import com.epam.secKill.entity.ProductInfo;
import org.junit.Rule;
import org.junit.Test;
import com.epam.EpamApplicationTests;
import com.epam.exception.BadRequestException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static org.hamcrest.Matchers.is;


public class TemperatureQueryServiceTest extends EpamApplicationTests {

//    @Autowired
//    private     TemperatureQueryService temperatureQueryService;
    @Test
    public void test() {
//        ExecutorService service = Executors.newCachedThreadPool();
//        for (int i = 0; i < 200; i++) {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e2) {
//                e2.printStackTrace();
//            }
//            Runnable runnable = () -> {
//                try {
//                     Assert.assertNotNull(temperatureQueryService.getTemperature
//                            ("江苏", "苏州", "吴中"));
////                    Assert.assertNotNull(temperatureQueryService.getTemperature
////                            ("", "苏州", "554444@@####$%%"));
//                } catch (ParamsException e) {
//                    e.printStackTrace();
//                }
//            };
//            service.execute(runnable);

   //     }
        List <ProductInfo> list = new ArrayList<>();
        ProductInfo p= new ProductInfo();
        p.setProductId("123");
        p.setProductName("aaa");
        list.add(p);
        p.setProductId("234");
        list.add(p);
        System.out.println(list.get(0).toString());
        System.out.println(list.get(1).toString());
    }

}

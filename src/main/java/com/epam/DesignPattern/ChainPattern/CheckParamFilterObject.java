package com.epam.DesignPattern.ChainPattern;


import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) //顺序排第1，最先校验
public class CheckParamFilterObject extends AbstractHandler {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response) {
        System.out.println("非空参数检查");
    }
}






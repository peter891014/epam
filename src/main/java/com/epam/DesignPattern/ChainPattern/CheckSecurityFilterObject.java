package com.epam.DesignPattern.ChainPattern;



import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *  安全校验对象
 */
@Component
@Order(2) //校验顺序排第2
public class CheckSecurityFilterObject extends AbstractHandler {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response) {
        //invoke Security check
        System.out.println("安全调用校验");
    }
}

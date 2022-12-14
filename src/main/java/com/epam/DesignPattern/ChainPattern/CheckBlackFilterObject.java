package com.epam.DesignPattern.ChainPattern;


import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *  黑名单校验对象
 *  1、Spring 4.2 利用@Order控制配置类的加载顺序，
 *
 *    2、Spring在加载Bean的时候，有用到order注解。
 *
 *    3、通过@Order指定执行顺序，值越小，越先执行
 *
 *    4、@Order注解常用于定义的AOP先于事物执行
 *
 *  @Order(XXXX)
 *
 * 默认情况下@订单注释遵循从低到高的顺序，即最低值具有高优先级。 这意味着它们首先出现在列表或数组中。 因为默认情况下，排序优先级设置为Ordered.LOWEST_PRECEDENCE。
 *
 * 如果首先需要最高值，那么我们需要将此值更改为Ordered.HIGHEST_PRECEDENCE。
 */
@Component
@Order(3) //校验顺序排第3
public class CheckBlackFilterObject extends AbstractHandler {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response) {
        //invoke black list check
        System.out.println("校验黑名单");
    }
}

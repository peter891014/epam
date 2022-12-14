package com.epam.secKill.controller;

import com.epam.secKill.entity.ProductInfo;
import com.epam.secKill.entity.SecOrder;
import com.epam.secKill.entity.SecProductInfo;
import com.epam.secKill.service.SecKillService;
import com.epam.secKill.utill.SecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/skill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, SecOrder> redisTemplate;
    /*
    下单，同时将订单信息保存在redis中，随后将数据持久化
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception{
        //判断是否抢光
        int amount = Integer.valueOf(stringRedisTemplate.opsForValue().get("stock"+productId));
        if (amount ==0){
            return "不好意思，活动结束啦";
        }
        //初始化抢购商品信息，创建虚拟订单。
        ProductInfo productInfo = new ProductInfo(productId);
        SecOrder secOrder = SecUtils.createDummyOrder(productInfo);
        //付款，付款时时校验库存，如果成功redis存储订单信息，库存加1
        if (!SecUtils.dummyPay()){
            log.error("付款慢啦抢购失败，再接再厉哦");
            return "抢购失败，再接再厉哦";
        }
        log.info("抢购成功 商品id=:"+ productId);
        //订单信息保存在redis中
        secKillService.orderProductMockDiffUser(productId,secOrder);
        return "订单数量: "+redisTemplate.opsForSet().size("order"+productId)+
                "  剩余数量:"+(2000 - Integer.valueOf(stringRedisTemplate.opsForValue().get("stock"+productId)));
    }
    /*
     在redis中刷新库存
     */
    @GetMapping("/refresh/{productId}")
    public String  refreshStock(@PathVariable String productId) {
        SecProductInfo secProductInfo = secKillService.refreshStock(productId);
        return "库存id为 "+productId +" <br>  库存总量为 "+secProductInfo.getStock();
    }

}

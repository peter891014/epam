package com.epam.secKill.utill;

import com.epam.secKill.entity.ProductInfo;
import com.epam.secKill.entity.SecProductInfo;
import com.epam.secKill.exception.SellException;
import com.epam.secKill.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductService productService;

    /*
    加锁
     */
    public boolean lock(String key,String value){

        //setIfAbsent对应redis中的setnx，key存在的话返回false，不存在返回true
        if ( redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        //两个问题，Q1超时时间
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //Q2 在线程超时的时候，多个线程争抢锁的问题
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key ,String value){
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch(Exception e){
            log.error("redis分布上锁解锁异常, {}",e);
        }

    }

    public SecProductInfo refreshStock(String productId){
        SecProductInfo secProductInfo = new SecProductInfo();
        ProductInfo productInfo = productService.findOne(productId);
        if (productId == null){
            throw new SellException(203,"秒杀商品不存在");
        }
        try{
            redisTemplate.opsForValue().set("stock"+productInfo.getProductId(),String.valueOf(productInfo.getProductStock()));
            String value = redisTemplate.opsForValue().get("stock"+productInfo.getProductId());
            secProductInfo.setProductId(productId);
            secProductInfo.setStock(value);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return secProductInfo;

    }
}

package com.epam.secKill.service.impl;

import com.epam.secKill.entity.SecOrder;
import com.epam.secKill.entity.SecProductInfo;
import com.epam.secKill.exception.SellException;
import com.epam.secKill.service.SecKillService;
import com.epam.secKill.service.SecOrderService;
import com.epam.secKill.utill.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private SecOrderService secOrderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, SecOrder> redisTemplate;

    private static final int TIMEOUT = 10 * 1000;

    @Override
    public  long orderProductMockDiffUser(String productId,SecOrder secOrder) {

        //加锁 setnx
        long orderSize = 0;
        long time = System.currentTimeMillis()+ TIMEOUT;
        boolean lock = redisLock.lock(productId, String.valueOf(time));
        if (!lock){
            throw  new SellException(200,"哎呦喂，人太多了");
        }
      //获得库存数量
        int stockNum = Integer.valueOf(stringRedisTemplate.opsForValue().get("stock"+productId));
        if (stockNum ==0) {
            throw new SellException(150, "活动结束");
        } else {
            //仓库数量减一
//            Lock lock1 = new ReentrantLock();
//            lock1.lock();
            stringRedisTemplate.opsForValue().decrement("stock"+productId,1);
            //redis中加入订单
            redisTemplate.opsForSet().add("order"+productId,secOrder);
//           lock1.unlock();
            orderSize = redisTemplate.opsForSet().size("order"+productId);
            if (orderSize >= 1000){
                //订单信息持久化,多线程写入数据库(效率从单线程的9000s提升到了9ms)
                Set<SecOrder> members = redisTemplate.opsForSet().members("order"+productId);
                List<SecOrder> memberList = new ArrayList<>(members);
                CountDownLatch countDownLatch = new CountDownLatch(4);
                new Thread(() -> {
                    for (int i = 0; i <memberList.size() /4 ; i++) {
                        secOrderService.save(memberList.get(i));
                        countDownLatch.countDown();
                    }
                }, "therad1").start();
                new Thread(() -> {
                    for (int i = memberList.size() /4; i <memberList.size() /2 ; i++) {
                        secOrderService.save(memberList.get(i));
                        countDownLatch.countDown();
                    }
                }, "therad2").start();
                new Thread(() -> {
                    for (int i = memberList.size() /2; i <memberList.size() * 3 / 4 ; i++) {
                        secOrderService.save(memberList.get(i));
                        countDownLatch.countDown();
                    }
                }, "therad3").start();
                new Thread(() -> {
                    for (int i = memberList.size() * 3 / 4; i <memberList.size(); i++) {
                        secOrderService.save(memberList.get(i));
                        countDownLatch.countDown();
                    }
                }, "therad4").start();

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               log.info("订单持久化完成");
            }
        }
        //解锁
        redisLock.unlock(productId,String.valueOf(time));
        return orderSize;
    }
    @Override
    public SecProductInfo refreshStock(String productId) {
        return redisLock.refreshStock(productId);
    }

}

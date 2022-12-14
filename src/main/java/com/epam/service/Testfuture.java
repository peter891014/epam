package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static java.lang.System.out;
@Service
public class Testfuture {
    @Autowired
    Executor taskExecutor ;
     Integer  a =0;
    public void test() throws ExecutionException, InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i=0;i<100;i++){
            taskExecutor.execute(()->{
                while(a<10000){
                    a++;
                    System.out.println(Thread.currentThread().getId()+":"+a);
                }
            });
            countDownLatch.countDown();
        }

       countDownLatch.await();

        System.out.println("ba"+a);
    }
}

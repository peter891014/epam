package com.epam.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static java.lang.System.out;

@Service
public class CompleteTest {

    @Autowired
     Executor taskExecutor ;

    public void test() throws ExecutionException, InterruptedException {

        final int[] a = {0};
        Supplier uSupplier = () -> {while(a[0] <100000){
                a[0]++;
            out.println(Thread.currentThread().getId()+":"+ a[0]);

        }
            return null;
        };
//        taskExecutor.execute(()->{
//            while(a[0] <10000){
//                a[0]++;
//                out.println(Thread.currentThread().getId()+":"+ a[0]);
//        }});
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> c1=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
        CompletableFuture<Integer> c2=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
        CompletableFuture<Integer> c3=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
        CompletableFuture<Integer> c4=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
//        CompletableFuture<Integer> c5=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
//        CompletableFuture<Integer> c6=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
//        CompletableFuture<Integer> c7=CompletableFuture.supplyAsync(uSupplier, taskExecutor);
        CompletableFuture<Integer> voidCompletableFuture = CompletableFuture.allOf(c1,c2,c3,c4).thenApply(
                (v) ->{
                    System.out.println(a[0]);
                    return a[0];
                }
        );
        int b= voidCompletableFuture.get();

        System.out.println("ba"+b+"time:"+ (System.currentTimeMillis()-start));
    }
}

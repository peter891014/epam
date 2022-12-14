package com.epam.service;

import com.ejlchina.http.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import com.ejlchina.http.HTTP;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: epam
 * @description: 测试注入线程池
 * @author: 作者名字
 * @create: 2022-05-21 15:48
 **/
public class TestInjectBean {

    @Qualifier("lizzThreadExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    CountDownLatch countDownLatch = new CountDownLatch(10);

    public <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                   RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {

        List<Future<T>> results = new ArrayList<>(times);

        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            results.add(threadPoolTaskExecutor.submit(task));
        }
        threadPoolTaskExecutor.shutdown();

        //   boolean executeCompleteWithinTimeout = threadPool.awaitTermination(executeTimeoutMillis, TimeUnit.MILLISECONDS);
//        while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
//        }
        countDownLatch.await();
        long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;

        // 线程池此时肯定已关闭，处理任务结果
        for (Future<T> r : results) {
            if (requestHandler != null) {
                requestHandler.handle(r.get());
            }
        }

        System.out.println("concurrent threads: " + concurrentThreads + ", times: " + times);
        System.out.println("total cost time(ms): " + totalCostTimeMillis + "ms, avg time(ms): " + ((double) totalCostTimeMillis / times));
        System.out.println("tps: " + (double) (times * 1000) / totalCostTimeMillis);
//        if (!executeCompleteWithinTimeout) {
//            System.out.println("Execute tasks out of timeout [" + executeTimeoutMillis + "ms]");
//
//            /*
//             * 取消所有任务
//             */
//            for (Future<T> r : results) {
//                r.cancel(true);
//            }
//        } else {
//            long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;
//
//            // 线程池此时肯定已关闭，处理任务结果
//            for (Future<T> r : results) {
//                if (requestHandler != null) {
//                    requestHandler.handle(r.get());
//                }
//            }
//
//            System.out.println("concurrent threads: " + concurrentThreads + ", times: " + times);
//            System.out.println("total cost time(ms): " + totalCostTimeMillis + "ms, avg time(ms): " + ((double) totalCostTimeMillis / times));
//            System.out.println("tps: " + (double) (times * 1000) / totalCostTimeMillis);
//        }
    }

    @Test
    public void TestM() throws InterruptedException, ExecutionException {
        concurrentTest(10, 500,
                () -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    String url = "http://localhost:8080/api/queryTemp";
                    Map params = new HashMap();
                    params.put("province", "江苏");
                    params.put("city", "苏州");
                    params.put("county", "吴江");
                    HTTP http = HTTP.builder().baseUrl(url).build();
                    String r = http.async("").addUrlParam(params).get().getResult().getBody().toString();
                    System.out.println("r: " + r + "thread:" + Thread.currentThread().getId());
                    countDownLatch.countDown();
                    return r;
                },
                result -> System.out.println("result: " + result), 10000);
    }


}

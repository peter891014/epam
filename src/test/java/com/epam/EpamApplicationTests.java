package com.epam;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.runner.RunWith;
import com.ejlchina.http.HTTP;
import com.epam.service.RequestHandler;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public  class EpamApplicationTests {

//    @Test
//    void contextLoads() {
//    }
//    @Before
//    public void init() {
//        System.out.println("开始测试-----------------");
//    }
//
//    @After
//    public void after() {
//        System.out.println("测试结束-----------------");
//    }

    @Qualifier("lizzThreadExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    public <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                   RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {

        List<Future<T>> results = new ArrayList<>(times);

        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            results.add(threadPoolTaskExecutor.submit(task));
        }
        threadPoolTaskExecutor.shutdown();

       //    boolean executeCompleteWithinTimeout = threadPool.awaitTermination(executeTimeoutMillis, TimeUnit.MILLISECONDS);
//        while (threadPoolTaskExecutor.getActiveCount()) {
//        }

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
                    return r;
                },
                result -> System.out.println("result: " + result), 10000);
    }
}

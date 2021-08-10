package com.epam.service;

import com.ejlchina.http.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class ControllerTest {

    public static <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                          RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool((int) concurrentThreads);
        List<Future<T>> results = new ArrayList<Future<T>>(times);

        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            results.add(executor.submit(task));
        }
        executor.shutdown();

        boolean executeCompleteWithinTimeout = executor.awaitTermination(executeTimeoutMillis,TimeUnit.MILLISECONDS);
        if (!executeCompleteWithinTimeout) {
            System.out.println("Execute tasks out of timeout [" + executeTimeoutMillis + "ms]");

            /*
             * 取消所有任务
             */
            for (Future<T> r : results) {
                r.cancel(true);
            }
        } else {
            long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;

            // 线程池此时肯定已关闭，处理任务结果
            for (Future<T> r : results) {
                if (requestHandler != null) {
                    requestHandler.handle(r.get());
                }
            }

            System.out.println("concurrent threads: " + concurrentThreads + ", times: "   + times);
            System.out.println("total cost time(ms): " + totalCostTimeMillis  + "ms, avg time(ms): " + ((double) totalCostTimeMillis / times));
            System.out.println("tps: " + (double) (times * 1000) / totalCostTimeMillis);
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        concurrentTest(300, 300,
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        String url="http://localhost:8080/api/queryTemp";
                        Map params= new HashMap();
                        params.put("province","江苏");
                        params.put("city","苏州");
                        params.put("county","吴江");
                        HTTP http = HTTP.builder().baseUrl(url).build();
                        String r=  http.async("").addUrlParam(params).get().getResult().getBody().toString();
                        return r;
                    }
                },
                new RequestHandler<String>() {
                    @Override
                    public void handle(String result) {
                        System.out.println("result: " + result);
                    }
                }, 6000);
    }


}

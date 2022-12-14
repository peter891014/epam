package com.epam.service;

import com.ejlchina.http.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class ControllerTest {

    public static <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                          RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {

    //    ExecutorService threadPool = Executors.newFixedThreadPool((int) concurrentThreads);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor((int)concurrentThreads, 30,
                500, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024));
        // 2.添加任务
//        addTask(threadPool);
//        // 3.判断线程池是否执行完
//        isCompleted(threadPool); // 【核心调用方法】
        List<Future<T>> results = new ArrayList<>(times);

        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            results.add(threadPool.submit(task));
        }
        threadPool.shutdown();

         boolean executeCompleteWithinTimeout = threadPool.awaitTermination(executeTimeoutMillis,TimeUnit.MILLISECONDS);
//        while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
//        }
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
        concurrentTest(10, 500,
                () -> {
                    try {
                        Thread.sleep(10);
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
                    System.out.println("r: " + r+"thread:"+Thread.currentThread().getId());
                    return r;
                },
                result -> System.out.println("result: " + result), 10000);
    }


}

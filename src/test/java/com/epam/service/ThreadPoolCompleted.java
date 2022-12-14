package com.epam.service;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池任务执行完成判断
 */
public class ThreadPoolCompleted {
    public static void main(String[] args) {
        // 1.创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20,
                0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024));
        // 2.添加任务
        addTask(threadPool);
        // 3.判断线程池是否执行完
        isCompleted(threadPool); // 【核心调用方法】
        // 4.线程池执行完
        System.out.println();
        System.out.println("线程池任务执行完成！");
    }

    /**
     * 方法1：isTerminated 实现方式
     * 判断线程池的所有任务是否执行完
     */
    private static void isCompleted(ThreadPoolExecutor threadPool) {
        threadPool.shutdown();
        while (!threadPool.isTerminated()) { // 如果没有执行完就一直循环
        }
    }

    /**
     * 给线程池添加任务
     */
    private static void addTask(ThreadPoolExecutor threadPool) {
        // 任务总数
        final int taskCount = 5;
        // 添加任务
        for (int i = 0; i < taskCount; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 随机休眠 0-4s
                        int sleepTime = new Random().nextInt(5);
                        TimeUnit.SECONDS.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("任务%d执行完成", finalI));
                }
            });
        }
    }

    private static void isCompletedByTaskCount(ThreadPoolExecutor threadPool) {
        while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
        }
    }

}

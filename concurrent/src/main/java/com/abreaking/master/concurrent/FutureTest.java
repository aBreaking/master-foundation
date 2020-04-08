package com.abreaking.master.concurrent;

import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2020/4/8
 */
public class FutureTest {



    public static void main(String args[]) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Future<String> t1 = threadPool.submit(() -> {
            System.out.println("t1");
            Thread.sleep(1000);
            countDownLatch.countDown();
            return "h1";
        });
        Future<String> t2 = threadPool.submit(() -> {
            System.out.println("t2");
            Thread.sleep(2000);
            return "h2";
        });
        //Future.get方法是有阻塞的！
        System.out.println("123");

    }
}

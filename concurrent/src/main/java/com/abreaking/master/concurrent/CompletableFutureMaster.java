package com.abreaking.master.concurrent;


import org.junit.Test;

import java.time.chrono.ThaiBuddhistEra;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

/**
 * CompletableFuture
 *
 * 使用Future获得异步执行结果时，要么调用阻塞方法get()，要么轮询看isDone()是否为true，这两种方法都不是很好，因为主线程也会被迫等待。
 *
 * 从Java 8开始引入了CompletableFuture，它针对Future做了改进，可以传入回调对象，当异步任务完成或者发生异常时，自动调用回调对象的回调方法。
 *
 * @link https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650
 * @link http://cmsblogs.com/?p=10723
 *
 * @author liwei
 * @date 2021/3/23
 */
public class CompletableFutureMaster {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("first is done");
            return "first->" + Thread.currentThread().getName();
        });
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("second is done");
            return "second->" + Thread.currentThread().getName();
        });

        CompletableFuture fs = CompletableFuture.allOf(first, second);
        fs.get();

        System.out.println("this is main thread");

        Thread.currentThread().join();
    }

    /**
     * 将CompletableFuture当作future用，只不过future.get方法会造成阻塞，CompletableFuture使用回调函数更灵活一些
     * @throws InterruptedException
     */
    @Test
    public void testSimple() throws InterruptedException, ExecutionException {
        boolean seeException = true;
        //创建异步任务，completableFuture自带线程池，也可以手动传进去
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("thread name is " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (seeException){
                throw new RuntimeException("some error");
            }
            return 100;
        },Executors.newSingleThreadExecutor());
        completableFuture.exceptionally(e->{
            return -1;
        });
        completableFuture.thenAccept(r->{
            //里面的内容也是在 CompletableFuture 线程池里执行的
            System.out.println(Thread.currentThread().getName()+ " the result is "+ r);
        });

        System.out.println("this is main thread task,you will see first");

        if (!seeException){
            Integer result = completableFuture.get(); //会阻塞,同future
            System.out.println("this is main thread another task,get result ->"+result);
        }


        Thread.sleep(3000);
    }
}

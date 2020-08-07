package com.abreaking.master.concurrent.threadpool;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 最近生产上一个thread.shutdown后 线程池仍然waiting的问题
 * @author liwei_paas
 * @date 2020/8/7
 */
public class PoolShutdownMaster {

    @Test
    public void runShutdown() throws Exception {

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                ExecutorService threadPool = Executors.newFixedThreadPool(8);
                for (int j = 0; j < 8; j++) {
                    threadPool.submit(()->{
                        System.out.println(Thread.currentThread().getName());
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            ThreadPoolExecutor lastPool = null;
            for (int i = 0; i < 100; i++) {
                System.out.println(lastPool);
                ThreadPoolExecutor pool = null;
                try {
                    pool = runThreadPool();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(pool);
                lastPool = pool;
            }
        }).start();
        System.in.read();
    }

    private ThreadPoolExecutor runThreadPool() throws Exception {
        int poolSize = 8;
        final CountDownLatch countDownLatch = new CountDownLatch(poolSize);
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        for (int j = 0; j < poolSize; j++) {
            final String name = String.valueOf(j);
            Future future = threadPool.submit(new MyCallable(name, countDownLatch));
        }
        countDownLatch.await();
        threadPool.shutdownNow();
        return threadPool;
    }

    @Test
    public void testShutdown() throws Exception {
        List<ThreadPoolExecutor> list = new ArrayList<>();

        int poolSize = 8;
        for (int i = 0; i < 10; i++) {

            final CountDownLatch countDownLatch = new CountDownLatch(poolSize);
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
            list.add(threadPool);
            for (int j = 0; j < poolSize; j++) {
                final String name = String.valueOf(i)+String.valueOf(j);
                threadPool.submit(new MyRunnable(name,countDownLatch));
            }
            countDownLatch.await();
            threadPool.shutdown();
            System.out.println(i + " is complete,thread is terminate " +threadPool.isTerminated());
            System.out.println(threadPool);
        }
        System.out.println("all is complete");
        while (true){
            list.forEach(threadPool -> System.out.println(threadPool.toString()+"->"+threadPool.isShutdown()+","+threadPool.isTerminated()));
            Thread.sleep(2000);
        }

    }



    @Test
    public void simpleThreadPool() throws IOException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 8; i++) {
            threadPool.submit(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
        Thread.sleep(2000);

        threadPool.shutdown();
        while (true){
            System.out.println(threadPool.isShutdown()+","+threadPool.isTerminated());
            Thread.sleep(2000);
        }

    }

    private static class MyCallable implements Callable {

        String name;
        CountDownLatch latch;

        public MyCallable(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"->"+name);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
            return name;
        }
    }

    private static class MyRunnable implements Runnable{

        String name;
        CountDownLatch latch;

        public MyRunnable(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"->"+name);
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                latch.countDown();
            }

        }
    }
}

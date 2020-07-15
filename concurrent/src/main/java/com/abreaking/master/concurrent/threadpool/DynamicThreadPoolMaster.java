package com.abreaking.master.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * 动态参数的线程池
 * 目的是在线程池运行期间，能够动态修改线程池参数
 * 参考文章：
 *  [如何设置线程池参数](https://mp.weixin.qq.com/s/9HLuPcoWmTqAeFKa1kj-_A)
 *  [Java线程池实现原理及其在美团业务中的实践](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)
 * @author liwei_paas
 * @date 2020/7/15
 */
public class DynamicThreadPoolMaster {
    static ThreadPoolExecutor THREAD_POOL ;
    static {
        int corePoolSize = 3;
        int maximumPoolSize = 5;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        THREAD_POOL = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
    }


    public static void main(String args[]) throws InterruptedException {
        newThreadForUpdateThreadPool();
        for (int i = 0; i < 50; i++) {
            THREAD_POOL.submit(new MyRunnable());
        }
        System.out.println("thread pool over");
        Thread.sleep(60000);
    }

    /**
     * 动态修改线程池的参数
     * 注意修改后的corePoolSize 必须大于原来的 maximumPoolSize
     */
    public static void newThreadForUpdateThreadPool(){
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("try to update thread pool");
            THREAD_POOL.setCorePoolSize(8);
            THREAD_POOL.setMaximumPoolSize(12);
        }).start();
    }


    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" is running,activeSize :"+THREAD_POOL.getActiveCount()+",CompletedTaskCount:"+THREAD_POOL.getCompletedTaskCount());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

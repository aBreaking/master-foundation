package com.abreaking.master.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过单线程池来无线提交任务，并且都是异步的
 * @author liwei_paas
 * @date 2020/4/17
 */
public class SingleThreadPoolMaster {

    public static void main(String args[]) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 1; i < 5; i++) {
            final String name = String.valueOf(i);
            final int s = i;
            executorService.submit(new Task(name,0));
        }

        Thread.sleep(1000);
        System.out.println("main");
        System.out.println(executorService.isShutdown());
        //executorService.shutdown();
        executorService.submit(new Task("a",1));
    }

    private static class Task implements Runnable{
        String name;
        int sleep;

        public Task(String name, int sleepSec) {
            this.name = name;
            this.sleep = sleepSec*1000;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name);
        }
    }
}

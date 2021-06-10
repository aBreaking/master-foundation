package com.abreaking.master.concurrent.condition;


import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * juc里的condition理解
 * @author liwei
 * @date 2021/6/10
 */
public class JucConditionMaster {

    static LinkedList queue = new LinkedList();

    static AtomicInteger  count  = new AtomicInteger();

    static int n = 10;

    static int ws = 500;
    static int hs = 200;


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new Writer(condition,lock)).start();
        new Thread(new Handler(condition,lock)).start();

        Thread.currentThread().join();
    }

    static class Writer implements Runnable{

        Condition condition;
        Lock lock;

        public Writer(Condition condition, Lock lock) {
            this.condition = condition;
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                try{
                    lock.lock();
                    System.out.println("writer->"+count.incrementAndGet());
                    queue.push(count.get());
                    condition.signal();
                    try {
                        Thread.sleep(ws);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            }

        }
    }

    static class Handler implements Runnable{

        Condition condition;
        Lock lock;

        public Handler(Condition condition, Lock lock) {
            this.condition = condition;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try{
                    if (queue.isEmpty()){
                        try {
                            System.out.println("handler wait");
                            condition.await();
                            System.out.println("handler wake");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("handler->"+queue.poll());
                    try {
                        Thread.sleep(hs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            }


        }
    }


}

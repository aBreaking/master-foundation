package com.abreaking.master.concurrent.condition;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 考虑现在这样的场景：
 *
 * 任务a在调用接口上报日志。如果失败了，把日志保存下来，唤醒任务b，由任务b处理失败的日志 再上报上去，如果还是上报失败，继续此逻辑
 *
 * @author liwei
 * @date 2021/6/11
 */
public class JucConditionReporterMaster {


    static LinkedList<Integer> failedQueue = new LinkedList<>();


    public static void main(String[] args) throws InterruptedException {
         Lock lock = new ReentrantLock();
         Condition condition = lock.newCondition();
        new Thread(new ErrorHandler(lock,condition)).start();

        AtomicInteger n = new AtomicInteger();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                report(n.getAndIncrement(),lock,condition);
            }
        },0,1000);



        Thread.currentThread().join();

    }

    static void report(int i,Lock lock,Condition condition){
        lock.lock();
        try{
            if (Math.random()<0.6) { //随机决定是成功还是失败
                System.out.println("success ->"+i +"->"+new SimpleDateFormat("ss").format(new Date()));
            }else{
                System.out.println("error ->" + i);
                handError(i,condition);
            }
        }finally {
            lock.unlock();
        }

    }

    static void handError(int i,Condition condition){
        failedQueue.push(i);
        condition.signal();
    }


    static class ErrorHandler implements Runnable{

        Lock lock;
        Condition condition;

        public ErrorHandler(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try{
                    if (failedQueue.isEmpty()){
                        System.out.println("handler wait");
                        condition.await();
                        System.out.println("handler wake up");
                    }
                    Integer poll = failedQueue.poll();
                    System.out.println("hand -> "+poll);
                    report(poll,lock,condition);
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }
    }

}

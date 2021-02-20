package com.abreaking.master.concurrent.cyclicbarrier;

import java.util.concurrent.Semaphore;

/**
 * Semaphore
 * 一个计数信号量。从概念上讲，信号量维护了一个许可集。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。每个 release() 添加一个许可，从而可能释放一个正在阻塞的获取者。但是，不使用实际的许可对象，Semaphore 只对可用许可的号码进行计数，并采取相应的行动。
 * from : http://cmsblogs.com/?p=2263
 * @author liwei_paas
 * @date 2021/2/20
 */
public class SemaphoreMaster {

    public static void main(String[] args) {
        int num = 5;
        Semaphore semaphore = new Semaphore(num);

        for (int i = 0; i < 10; i++) {
            int sleepTime = i*1000;
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+" try come");
                    semaphore.acquire(); //获取信号，semaphore不为0才可以继续下一步
                    System.out.println(Thread.currentThread().getName()+" has come");
                    Thread.sleep(sleepTime);
                    System.out.println(Thread.currentThread().getName()+" exit");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }

            }).start();
        }

    }
}

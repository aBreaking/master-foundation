package com.abreaking.master.ads.leetcode.thread;

import java.util.concurrent.Semaphore;

/**
 * 按序打印
 *
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 *
 * 一个将会调用 first() 方法
 * 一个将会调用 second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * @author liwei
 * @date 2021/2/24
 */
public class PrintInOrderMaster {

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        new Thread(()->{
            try {
                foo.first(()-> System.out.println("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                foo.second(()-> System.out.println("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                foo.third(()-> System.out.println("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.currentThread().join();
        System.out.println("over");
    }


    static class Foo {


        Semaphore s1 = new Semaphore(0);
        Semaphore s2 = new Semaphore(0);

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();

            s1.release();

        }

        public void second(Runnable printSecond) throws InterruptedException {

            s1.acquire();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();

            s2.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            s2.acquire();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }


}

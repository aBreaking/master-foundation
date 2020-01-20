package com.abreaking.master.concurrent;

/**
 * InheritableThreadLocal
 * 可以通过threadLocal在同一个线程中进行值传递，但是在父子线程中就不能进行值传递了，因为不是同一个线程，所以对应的ThreadLocalMap是不一样的
 * 父子线程中进行指传递可以通过InheritableThreadLocal 实现
 *
 * @author liwei_paas
 * @date 2020/1/20
 */
public class InheritableThreadLocalMaster {
    //public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static String get() {
        return threadLocal.get();
    }

    public static void set(String value) {
        threadLocal.set(value);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            set("this is "+ i);
            new Thread(()->System.out.println(Thread.currentThread().getName() + ":" + get())).start();
        }
    }

}

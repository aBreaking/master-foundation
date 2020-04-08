package com.abreaking.master.concurrent;

import java.util.LinkedHashMap;

/**
 * 考虑实现一个key 去关联一个Thread,进而获取到该thread的value
 * @author liwei_paas
 * @date 2020/1/19
 */
public class SessionLocalMaster  {

    //TODO 实现lRU算法：https://my.oschina.net/sfshine/blog/1594942
    private static LinkedHashMap<String,Thread> sessionThreadCache = new LinkedHashMap<>(16);

    public static boolean hasTask(String sessionId){
        if (!sessionThreadCache.containsKey(sessionId)){
            return false;
        }
        Thread thread = sessionThreadCache.get(sessionId);
        if (!thread.isAlive()){
            return false;
        }
        return true;
    }

    public static void exec(String sessionId) throws InterruptedException {
        if (sessionThreadCache.containsKey(sessionId)){
            Thread thread = sessionThreadCache.get(sessionId);
            //确定线程正在运行
            if (thread.isAlive()){
                System.out.println(thread.getName()+" is alive");
                return;
            }else{
                System.out.println(thread.getName()+"is "+thread.getState());
                sessionThreadCache.remove(sessionId);
            }
        }
        synchronized (sessionThreadCache){
            if (!sessionThreadCache.containsKey(sessionId)){
                MyTask myTask = new MyTask();
                //这里的线程创建完全可以使用旧的方法
                Thread thread = new Thread(myTask, sessionId);

                sessionThreadCache.put(sessionId,thread);
                thread.start();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String s1 = "a1";
        String s2 = "a2";
        exec(s1);
        exec(s1);
        exec(s1);
        exec(s1);
        exec(s2);
        exec(s2);
    }

    static class MyTask implements Runnable{

        public void run(){
            System.out.println(Thread.currentThread().getName()+" is doSomething");
        }
    }

}

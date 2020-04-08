package com.abreaking.foundation.jvm;

import javafx.scene.SubScene;

import java.util.*;

/**
 * LinkedHashMap来实现一个LRU算法
 * @author liwei_paas
 * @date 2020/4/8
 */
public class LinkedHashMapTest {

    private static Map lru = new LRU(3);

    public static void main(String args[]) throws InterruptedException {
        lru = Collections.synchronizedMap(lru);
        put("a",1000L);
        put("b",2000L);
        put("c",3000L);
        Thread.sleep(1000);
        System.out.println("1->"+lru);
        put("d",4000L);
        Thread.sleep(1000);
        System.out.println(lru);

    }

    public static void put(String key, final Long value){
        new Thread(()->{
            lru.put(key,value);
        }).start();
    }


    static class LRU<K,V> extends LinkedHashMap{

        private int maxSize;

        public LRU(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size()>maxSize;
        }
    }
}

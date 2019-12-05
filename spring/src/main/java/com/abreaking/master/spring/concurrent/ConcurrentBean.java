package com.abreaking.master.spring.concurrent;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试spring的bean线程安全情况
 */
@Component
public class ConcurrentBean {

    /**
     * 要做的事情
     */
    public void pull(){
        String key = "key";
        String value = Thread.currentThread().getName();
        Client client = new Client(value);
        Map<String, Client> map = Collections.singletonMap(key, client);
        try{
            System.out.println(value+"->pull start");
            Map<String, String> ret = doPull(map);
            System.out.println(ret.get(key)+"->"+client);
        }finally {
            client.close();
        }
    }

    public Map<String,String> doPull(Map<String,Client> map){
        final Map<String,String> ret = new HashMap<>();
        map.forEach((k,v)->ret.put(k,v.doPull()));
        return ret;
    }

    /**
     * 表示事情完成
     */
    private void close(){
    }
}

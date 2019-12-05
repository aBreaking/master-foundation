package com.abreaking.master.spring.concurrent;

import java.util.Date;
import java.util.Random;

/**
 * 模拟一个建立连接并操作的工具
 * @author liwei_paas
 * @date 2019/12/3
 */
public class Client {

    private boolean shutdown;

    private String clientName;

    public Client(String name){
        shutdown = false;
        this.clientName = name;
    }

    public String doPull(){
        if (shutdown){
            System.out.println(clientName +"-> client is closed!!!");
            return null;
        }
        //这里进行一些较为耗时的逻辑计算
        Random random = new Random();
        int i = random.nextInt(Integer.MAX_VALUE);
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                if (shutdown){
                    return clientName+"-> client is closed";
                }

            }
        }
        
        Date date = new Date();
        return clientName+"->client->"+date.toString();
    }

    public void close(){
        shutdown = true;
    }

}

package com.abreaking.master.spring.concurrent;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class ComponentClient extends Client {

    private boolean shutdown = false;

    public ComponentClient() {
        super("");
    }

    public String doPull(){
        String clientName = Thread.currentThread().getName();
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

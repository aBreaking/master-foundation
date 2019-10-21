package com.abreaking.foundation.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 * @author liwei_paas
 * @date 2019/9/30
 */
public class OutOfMemoryErrorTest {

    public static void main(String args[]) throws Throwable {
        List<OOMObject> list = new ArrayList<OOMObject>();
        try{
            while (true) {
                list.add(new OOMObject());
            }
        }catch (Throwable e){
            System.out.println(list.size());
            throw e;
        }

    }

    static class OOMObject{
    }
}

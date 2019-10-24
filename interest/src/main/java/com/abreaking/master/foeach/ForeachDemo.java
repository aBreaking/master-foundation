package com.abreaking.master.foeach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @{USER}
 * @{DATE}
 */
public class ForeachDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        final List ret = new ArrayList();
        list.forEach((s)->ret.add(s));
        System.out.println(ret);
    }



    public static void testEmptyList(){
        List<String> list = Collections.emptyList();
        /*throws UnsupportedOperationException*/
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);
    }
}

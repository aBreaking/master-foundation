package com.abreaking.master.jdk.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jdk1.8的流式操作
 * see: http://hollischuang.gitee.io/tobetopjavaer/#/basics/java-basic/stream
 * @author liwei_paas
 * @date 2021/2/18
 */
public class StreamSimpleMaster {

    @Test
    public void master01(){
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu", "wangermazi");
        Stream<String> stream = list.stream();
        stream.
                map(k->k.substring(0,1).toUpperCase()+k.substring(1))
                .filter(k->k.contains("a"))
                .distinct()
                .skip(1)
                .limit(2)
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

    @Test
    public void master02(){
        Stream<Integer> stream = Stream.of(1, 3, 7, 2, 4, 9, 5);
        stream.map(k->k*k).sorted().
                forEach(System.out::print);
    }
}

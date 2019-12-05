package com.abreaking.master.spring.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @{USER}
 * @{DATE}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class ConcurrentBeanTest {

    @Resource
    ConcurrentBean concurrentBean;

    @Test
    public void test02(){

    }

    @Test
    public void test01() throws InterruptedException {
        int size = 8;
        ExecutorService pool = Executors.newFixedThreadPool(size);
        final CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            pool.submit(()->{
                try{
                    concurrentBean.pull();
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        System.out.println("main is over");
    }
}

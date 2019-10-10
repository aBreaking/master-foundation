package com.abreaking.master.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorKwky {

    /**
     * 直接创建大量对象出现oom，很明显，这个是堆的溢出
     * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
     */
    @Test
    public void OomInLargeObjTest(){
        List<OutOfMemoryErrorKwky> list = new ArrayList<OutOfMemoryErrorKwky>();
        for (;;){
            list.add(new OutOfMemoryErrorKwky());
        }
    }


    /**
     * 模拟常量池出现 oom
     * 这个还是没有模拟出来
     *-XX:PermSize=2K -XX:MaxPermSize=2K
     */
    @Deprecated
    @Test
    public void OomInRuntimeConstantPoolTest(){
        List<String> list = new ArrayList<String>();
        int i=0;
        for (;;){
            list.add(String.valueOf(i++).intern());
        }
    }

    /**
     * 通过cglib大量创建class对象模拟oom。 这个是方法区里面的溢出
     * -XX:PermSize=1M -XX:MaxPermSize=1M
     */
    @Test
    public void OomInCglibCreateClassTest(){
        for (;;){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OutOfMemoryErrorKwky.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,objects);
                }
            });
            enhancer.create();
        }
    }
}

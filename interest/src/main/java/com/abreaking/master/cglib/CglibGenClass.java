package com.abreaking.master.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用cglib动态生成类
 * @author liwei_paas
 * @date 2019/10/21
 */
public class CglibGenClass {
    public static void main(String[] args) throws ClassNotFoundException {

        // 设置类成员属性
        Map<String, Class<?>> propertyMap = new HashMap();

        propertyMap.put("id", Class.forName("java.lang.Integer"));

        propertyMap.put("name", Class.forName("java.lang.String"));

        propertyMap.put("address", Class.forName("java.lang.String"));

        // 生成动态 Bean
        CglibBean bean = new CglibBean(propertyMap);
        // 给 Bean 设置值
        bean.setValue("id", 123);
        bean.setValue("name", "454");
        bean.setValue("address", "789");

        // 获得bean的实体
        Object object = bean.getObject();

        Class clazz = object.getClass();
        // 查看生成的类名
        System.out.println(clazz.getName());
        // 通过反射查看所有方法名
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    public static class CglibBean {
        /**
         * 生成的对象
         */
        private Object object = null;

        /**
         * 属性map
         */
        private BeanMap beanMap = null;

        public CglibBean() {
            super();
        }

        public CglibBean(Map<String, Class<?>> propertyMap) {
            generateBean(propertyMap);
            this.beanMap = BeanMap.create(this.object);
        }


        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        public Object getValue(String property) {
            return beanMap.get(property);
        }

        public Object getObject() {
            return this.object;
        }


        /**
         * 根据指定属性map 动态生成对象
         * @param propertyMap
         */
        private void generateBean(Map<String, Class<?>> propertyMap) {

            if (propertyMap == null || propertyMap.isEmpty()) {
                return;
            }

            BeanGenerator generator = new BeanGenerator();

            for (Map.Entry<String, Class<?>> entry : propertyMap.entrySet()) {
                generator.addProperty(entry.getKey(), entry.getValue());
            }

            this.object = generator.create();
        }
    }
}

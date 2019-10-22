package com.abreaking.master.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * cglib生成某个父类的子类
 * @author liwei_paas
 * @date 2019/10/22
 */
public class GenClassWithSuper {

    public static void main(String args[]) throws ClassNotFoundException {
        CglibBean cglibBean = new CglibBean();
        cglibBean.setData("hello");
        cglibBean.setAlarm(18);
        SuperClass object = cglibBean.getObject();
        System.out.println(object.getData());
        System.out.println(object.getAlarm());

    }
}
class CglibBean {
    /**
     * 生成的对象
     */
    private SuperClass object = null;

    /**
     * 属性map
     */
    private BeanMap beanMap = null;


    public CglibBean() {
        generateBean();
        this.beanMap = BeanMap.create(this.object);
    }

    public void setAlarm(Integer value){
        setValue("alarm",value);
    }
    public void setData(String data){
        setValue("data",data);
    }

    private void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    public SuperClass getObject() {
        return this.object;
    }


    /**
     * 根据指定属性map 动态生成对象
     */
    private void generateBean() {

        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(SuperClass.class);
        generator.addProperty("data",String.class);
        generator.addProperty("alarm",Integer.class);

        this.object = (SuperClass) generator.create();
    }
}

class SuperClass{
    public String getData(){
        return null;
    }

    public Integer getAlarm(){
        return 0;
    }
}

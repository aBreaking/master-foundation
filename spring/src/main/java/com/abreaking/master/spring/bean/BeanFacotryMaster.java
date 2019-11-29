package com.abreaking.master.spring.bean;

import com.abreaking.master.spring.component.InterfaceInjectMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @{USER}
 * @{DATE}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class BeanFacotryMaster {
    @Resource
    BeanFactory beanFactory;

    @Test
    public void test01(){
        InterfaceInjectMaster interfaceInjectMaster = (InterfaceInjectMaster) beanFactory.getBean("interfaceInjectMaster");
        interfaceInjectMaster.print();
    }
}
package com.abreaking.master.spring.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * @author liwei_paas
 * @date 2020/7/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class Main {

    @Resource
    TxPropagationMasterA a;

    @Test
    public void rollBack(){
        a.mandatoryA();
    }

    @Resource
    TxInvalidMaster txInvalidMaster;
    @Test
    public void testTxInvalidMaster() throws IOException {
        txInvalidMaster.rollbackForA();
    }
}

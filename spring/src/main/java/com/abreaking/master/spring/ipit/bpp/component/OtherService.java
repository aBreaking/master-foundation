package com.abreaking.master.spring.ipit.bpp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * @author liwei_paas
 * @date 2019/12/10
 */
@Component
public class OtherService implements Serializable {

    @Value("other")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

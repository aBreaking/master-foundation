package com.abreaking.master.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Java 调用js脚本 的一些骚操作
 * @author liwei_paas
 * @date 2019/10/29
 */
public class ScriptDemo {

    public static void main(String[] args) throws Exception {
        testJsExpress();
    }

    /**
     * 运算符'=' 需要转义成 '=='
     * @throws ScriptException
     */
    public static void testJsExpress() throws ScriptException {
        String value = "1.3";
        String operator = ">=";
        String threshold = "1.2";
        String express = value+operator+threshold;
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine javascript = scriptEngineManager.getEngineByName("javascript");
        Boolean eval = (Boolean) javascript.eval(express);
        System.out.println(eval);
    }
}

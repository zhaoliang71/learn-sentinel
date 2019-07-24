package com.bj.zl.learn.flow.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bj.zl.learn.flow.fallback.FallbackUtils;
import com.bj.zl.learn.flow.handler.HandlerFallUtils;

import java.io.IOException;


public class SentinelResourceServiceImpl {


    /**
     * 抛出异常式定义资源
     */
    public String castExceptionMethod(int i) {

        try(Entry entry = SphU.entry("castExceptionMethod")){

            //业务
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "castExceptionMethod:" + i;
        } catch (BlockException e) {
            //限流降级方法
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return "castExceptionMethod--fall:" + i;
        }
    }

    /**
     * 返回布尔值定义资源
     * @param i
     * @return
     */
    public String booleanMethod(int i){

        if (SphO.entry("booleanMethod")){
            try {
                Thread.sleep(100);
                return "booleanMethod:"+i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                SphO.exit();
            }
        }else {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "booleanMethod--fall:"+i;
        }
        return "";
    }

    ////////////////////////////////////////////////////////////////////////

    /**
     * 注解方式  handler
     * blockHandler
     * 若同时配置了 blockHandler 和 fallback，限流时抛出BlockException时，只会进入blockHandler
     * @param i
     * @return
     */
    @SentinelResource(value = "annotationHandlerMethod" , blockHandler = "annotationHandlerMethod",blockHandlerClass = HandlerFallUtils.class)
    public String annotationHandlerMethod(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + "=execute annotationHandlerMethod";
    }



    /**
     * 注解方式  fallback FallbackUtils中降级
     * 用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理
     * @return
     */
    @SentinelResource(value = "annotationMethod",fallback = "annotationFallMethod",fallbackClass = FallbackUtils.class)
    public String annotationMethod(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + ":execute method";
    }

    /**
     * 注解方式  fallback FallbackUtils中降级
     * 用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理
     * @return
     */
    @SentinelResource(value = "annotationIgnoreMethod",
            fallback = "annotationIgnoreMethod",fallbackClass = FallbackUtils.class,
            exceptionsToIgnore = InterruptedException.class)
    public String annotationIgnoreMethod(int i) throws Exception {
        Thread.sleep(200);

        if (i%2 == 0){
            throw new InterruptedException();
        }else if (i%2 == 1){
            throw new IOException();
        }
        return i + ":execute method";
    }

    /**
     * limitApp 降级
     * @param i
     * @param appName
     * @return
     */
    @SentinelResource(value = "annotationLimitNameMethod",fallback = "annotationLimitNameMethod",fallbackClass = FallbackUtils.class)
    public String annotationLimitNameMethod(int i,String appName){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + "=app:"+appName+",execute method";
    }


}

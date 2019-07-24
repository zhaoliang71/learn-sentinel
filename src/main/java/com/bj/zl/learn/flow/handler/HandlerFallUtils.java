package com.bj.zl.learn.flow.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class HandlerFallUtils {


    /**
     * 需要public static 参数需要多一个BlockException
     * @param i
     * @param d
     * @return
     */
    public static String annotationHandlerMethod(int i,BlockException d){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + "=execute annotationHandlerMethod--fall:"+d.getMessage();
    }

}

package com.bj.zl.learn.flow;


import com.alibaba.csp.sentinel.context.ContextUtil;
import com.bj.zl.learn.flow.rule.FlowRuleUtil;
import com.bj.zl.learn.flow.service.SentinelResourceServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    /**
     *  抛异常式 定义资源
     */
    @Test
    public void testCastExceptionMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = sentinelResourceService.castExceptionMethod(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }

    /**
     * 布尔式定义资源
     */
    @Test
    public void testBooleanMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = sentinelResourceService.booleanMethod(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }

    /**
     * 注解式 handler
     */
    @Test
    public void testAnnotationHandlerMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = sentinelResourceService.annotationHandlerMethod(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }

    /**
     * 注解式 fallback
     */
    @Test
    public void testAnnotationMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = sentinelResourceService.annotationMethod(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }

    /**
     * 注解式 fallback  InterruptedException  IOException
     *
     * exceptionsToIgnore = InterruptedException.class
     */
    @Test
    public void testAnnotationIgnoreMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = null;
            try {
                logStr = sentinelResourceService.annotationIgnoreMethod(i++);
            } catch (Exception e) {
                System.out.println(i+"=annotationIgnoreMethod error:"+e.getMessage());
            }
            if (StringUtils.isNotEmpty(logStr)){
                System.out.println(logStr);
            }
            if(i>20){
                return;
            }
        }
    }

    /**
     * limitApp
     */
    @Test
    public void testAnnotationLimitNameMethod(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = null;
            if (i%2 == 0){
                ContextUtil.enter("annotationLimitNameMethod","limitApp");
                logStr = sentinelResourceService.annotationLimitNameMethod(i++,"limitApp");
                ContextUtil.exit();
            }else {
                logStr = sentinelResourceService.annotationLimitNameMethod(i++,"whiteApp");
            }
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }

}

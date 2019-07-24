package com.bj.zl.learn.flow.rule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.Arrays;

public class FlowRuleUtil {


    public static void initRule(){

        FlowRuleManager.loadRules(Arrays.asList(
                flowRuleBuilder("castExceptionMethod",RuleConstant.FLOW_GRADE_QPS,5,"default"),
                flowRuleBuilder("booleanMethod",RuleConstant.FLOW_GRADE_QPS,5,"default"),
                flowRuleBuilder("annotationHandlerMethod",RuleConstant.FLOW_GRADE_QPS,5,"default"),
                flowRuleBuilder("annotationMethod",RuleConstant.FLOW_GRADE_QPS,5,"default"),
                flowRuleBuilder("annotationIgnoreMethod",RuleConstant.FLOW_GRADE_QPS,3,"default"),
                //同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕
                flowRuleBuilder("annotationLimitNameMethod",RuleConstant.FLOW_GRADE_QPS,3,"limitApp"),
                flowRuleBuilder("annotationLimitNameMethod",RuleConstant.FLOW_GRADE_QPS,10,"whiteApp")
        ));

    }


    private static FlowRule flowRuleBuilder(String resourceName, int grade, int count, String limitName){
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setGrade(grade);//RuleConstant.FLOW_GRADE_THREAD 按线程数限流，   默认值 FLOW_GRADE_QPS按QPS限流
        rule.setCount(count);//QPS 或 线程的限制数

        //调用方限流
        //默认值 "default" 表示不区分调用者，来自任何调用着的请求都将进行限流统计。
        //{some_origin_name}：表示针对特定的调用者，只有来自这个调用者的请求才会进行流量控制。
        //other：表示针对除 {some_origin_name} 以外的其余调用方的流量进行流量控制
        //同一个资源名可以配置多条规则，规则的生效顺序为：{some_origin_name} > other > default
        rule.setLimitApp(limitName);


        //rule.setControlBehavior() 流量控制效果（默认值 直接拒绝、Warm Up、匀速排队）
        /*warm up  在setWarmUpPeriodSec时间内 逐步达到调用最大值count
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        rule.setCount(count)
        rule.setWarmUpPeriodSec(10);*/

       /*均匀队列
        当请求到来的时候，如果当前请求距离上个通过的请求通过的时间间隔不小于预设值，则让当前请求通过；
        否则，计算当前请求的预期通过时间，如果该请求的预期通过时间小于规则预设的 timeout 时间，则该请求会等待直到预设时间到来通过；反之，则马上抛出阻塞异常。
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        rule.setCount(10);
        rule.setMaxQueueingTimeMs(20*1000);*/
       return rule;
    }
}

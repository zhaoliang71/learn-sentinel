package com.bj.zl.learn.flow.fallback;

public class FallbackUtils {


    /**
     * 返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * @param i
     * @return
     */
    public static String annotationFallMethod(int i){

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + "=execute fallbackMethod";
    }

    /**
     * 可以加Throwable 参数
     * exceptionsToIgnore 中的异常会抛出，其他异常走降级
     * @param i
     * @param throwable
     * @return
     */
    public static String annotationIgnoreMethod(int i,Throwable throwable){

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + "=execute annotationIgnoreMethod:"+throwable;
    }

    /**
     * limitApp
     * @param i
     * @param appName
     * @return
     */
    public static String annotationLimitNameMethod(int i,String appName){

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + "=execute annotationLimitNameMethod,app:"+appName;
    }
}

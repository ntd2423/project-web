package com.ntd.aop.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志切面类,定义了许多通知
 */
public class LogXMLAspect {

    private static final Log logger = LogFactory.getLog(LogXMLAspect.class);

    //任何通知方法都可以将第一个参数定义为 org.aspectj.lang.JoinPoint类型
    public void before(JoinPoint call) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();

        logger.debug("前置通知:" + className + "类的" + methodName + "方法执行");
    }

    public void afterReturn() {
        logger.debug("后置通知:方法正常结束后执行");
    }

    public void after(){
        logger.debug("最终通知:不管方法有没有正常结束，一定会执行");
    }

    public void afterThrowing() {
        System.out.println("异常抛出后通知:方法执行时出异常后执行");
    }

    //用来做环绕通知的方法可以第一个参数定义为org.aspectj.lang.ProceedingJoinPoint类型
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        Object result = null;
        this.before(call);//相当于前置通知
        try {
            result = call.proceed();
            this.afterReturn(); //相当于后置通知
        } catch (Throwable e) {

            this.afterThrowing();  //相当于异常抛出后通知
            throw e;
        }finally{
            this.after();  //相当于最终通知
        }

        return result;
    }

}

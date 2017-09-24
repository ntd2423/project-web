package com.ntd.aop.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 日志切面类,定义了许多通知
 */
@Aspect
public class LogAnnotationAspect {

    private static final Log logger = LogFactory.getLog(LogAnnotationAspect.class);

    //定义切入点
    @Pointcut("execution(* com.ntd.service.impl..*.*(..))")
    private void logPointcut(){}

    //任何通知方法都可以将第一个参数定义为 org.aspectj.lang.JoinPoint类型
    //针对指定的切入点表达式选择的切入点应用前置通知
    @Before("execution(* com.ntd.service.impl..*.*(..))")
    public void before(JoinPoint call) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();

        logger.debug("前置通知:" + className + "类的" + methodName + "方法执行");
    }

    @AfterReturning("logPointcut()")
    public void afterReturn() {
        logger.debug("后置通知:方法正常结束后执行");
    }

    @After("logPointcut()")
    public void after(){
        logger.debug("最终通知:不管方法有没有正常结束，一定会执行");
    }

    @AfterThrowing("logPointcut()")
    public void afterThrowing() {
        System.out.println("异常抛出后通知:方法执行时出异常后执行");
    }

    //用来做环绕通知的方法可以第一个参数定义为org.aspectj.lang.ProceedingJoinPoint类型
    //@Around("logPointcut()")
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

package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 概要描述：AOP示例
 * 详细描述：Advice(各种通知)，用来定义横切逻辑，即在连接点上准备织入什么样的逻辑
 *
 * @author:程圣严 日期：2022-10-15 17:11
 */
//@Component
//@Aspect
public class AlphaAspect {

    /**
     * 定义要对哪些类的哪些方法做织入(增强)
     * 切点中表达式的写法说明：
     *      第一个*代表方法的返回值是任意的(即对任何返回值的方法都做增强)
     *      com.nowcoder.community.service这个对哪个包下的类做增强
     *      第2、3个*、(..) 代表com.nowcoder.community.service下的所有类的所有方法的任意参数(参数任意)
     */
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before() {
        System.out.println("before 前置通知");
    }

    @After("pointCut()")
    public void after() {
        System.out.println("after 后置通知");
    }

    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("afterReturning 方法返回值后通知");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("afterThrowing 在抛出异常后的通知");
    }

    /**
     * 环绕通知(对jointPoint连接点做前后的增强)
     * @param jointPoint
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint jointPoint) throws Throwable {
        System.out.println("around 环绕通知，在方法执行前执行的通知");
        Object object = jointPoint.proceed();
        System.out.println("around 环绕通知，在方法执行后执行的通知");
        return object;
    }
}

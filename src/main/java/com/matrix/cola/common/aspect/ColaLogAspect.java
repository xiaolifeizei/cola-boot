package com.matrix.cola.common.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 操作日志注解AOP
 *
 * @author : cui_feng
 * @since : 2022-04-08 10:16
 */
@Aspect
@Component
public class ColaLogAspect {

    @Pointcut("@annotation(com.matrix.cola.common.annotation.ColaLog)")
    private void pointcut() {}

    @Before("pointcut()")
    public void advice(JoinPoint joinPoint) {
        System.out.println("--- Before---");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("---Around---");
        Object result = joinPoint.proceed();
        System.out.println("---After---");
        return result;
    }

}

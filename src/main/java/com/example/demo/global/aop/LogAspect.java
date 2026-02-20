package com.example.demo.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	//
	@Around("execution(* com.example.demo.example.*Service.*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 원래 해야 할 '핵심 로직' 실행

        long end = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature() + " 실행 시간: " + (end - start) + "ms");
        
        return result;
    }
}
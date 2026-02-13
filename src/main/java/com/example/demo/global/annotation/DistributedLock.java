package com.example.demo.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit; // <--- 이 줄을 반드시 추가하세요!

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String key(); // 락의 키 (예: 'user:' + #id)
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    long waitTime() default 5L;    // 락 획득 대기 시간
    long leaseTime() default 3L;   // 락 점유 시간
}

package com.example.demo.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit; // <--- 이 줄을 반드시 추가하세요!

// Distribution 분산하다

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String key(); // 락을 식별하는 키 ex) user:1, product:1
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    long waitTime() default 5L;    // 락이 이미 선점되어 있다면, 내가 얼마나 기다릴지 결정
    long leaseTime() default 3L;   // 락을 획득한 후, 최대 몇 초 동안 가지고 있을지 정합니다.
}

package com.example.demo.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import com.example.demo.global.annotation.DistributedLock;
import com.example.demo.global.config.RedisLockManager;
import com.example.demo.global.util.CustomSpringELParser;

import lombok.RequiredArgsConstructor;

// @Aspect : 스프링에게 해당 클래스는 비즈니스 로직이 아니라, 여러 도메인(User, Order 등)에 흩어져서 반복 실행될 공통 기능이야"**라고 알려주는 역할입니다.
// 			 어노테이션이 있어야 스프링이 이 클래스 안에 있는 코드들을 다른 메서드 실행 전/후에 끼워 넣을 준비를 합니다.
// @Component : 내가 만든 라이브러리

@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {
	
	private final RedisLockManager redisLockManager; // 분리된 매니저 주입
	
	
	// @Around("@annotation(distributedLock)"): "언제 실행할 것인가?"
	// 대상 메서드의 실행 전과 후 모두를 제어하겠다는 의미입니다.
    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        
        // 1. 파서 사용
        String key = (String) CustomSpringELParser.getDynamicValue(
                signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());

        RLock rLock = redisLockManager.getLock("LOCK:" + key);

        try {
            // 2. 락 획득 로직
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) return false;

            return joinPoint.proceed(); // 비즈니스 로직 실행
        } finally {
            // 3. 매니저를 통한 안전한 해제
        	redisLockManager.unlock(rLock);
        }
    }
}

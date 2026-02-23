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

// @DistributedLock가 명시되어있는 메서드가 실행될 때, 실행을 가로채서 앞뒤로 락 로직을 끼워넣음


@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {
	
	private final RedisLockManager redisLockManager; // 분리된 매니저 주입
	
	
	// ProceedingJoinPoint : aop의 핵심 인터페이스 중 하나로 가로챈 메서드의 실행 권한을 쥐고 있음
	
	// 대상 메서드의 실행 전과 후 모두를 제어하겠다는 의미입니다.
    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
    	
    	System.out.println("joinPoint:::" + joinPoint);
    	System.out.println("distributedLock:::" + distributedLock);
    	
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("signature:::" + signature);
        System.out.println("signature.getParameterNames():::" + signature.getParameterNames());
        
        // 1. CustomSpringELParser을 이용해서 키 생성
        String key = (String) CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        System.out.println("key:::" + key);
        
        // 2. RedisLockManager를 통해 락 객체 획득
        RLock rLock = redisLockManager.getLock("LOCK:" + key);
        System.out.println("락 이름: " + rLock.getName());
        System.out.println("현재 잠김 여부: " + rLock.isLocked());
        System.out.println("내 스레드 점유 여부: " + rLock.isHeldByCurrentThread());
        System.out.println("재진입 횟수: " + rLock.getHoldCount());

        try {
            // 3. 락 획득 시도
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) return false;

            // 4. 비즈니스 로직 실행
            return joinPoint.proceed(); 
        } finally {
            // 5. 매니저를 통한 안전한 해제
        	redisLockManager.unlock(rLock);
        }
    }
}

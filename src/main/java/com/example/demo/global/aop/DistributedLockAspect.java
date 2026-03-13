package com.example.demo.global.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import lombok.extern.slf4j.Slf4j;



// @Aspect : 스프링에게 해당 클래스는 비즈니스 로직이 아니라, 여러 도메인(User, Order 등)에 흩어져서 반복 실행될 공통 기능이야"**라고 알려주는 역할입니다.
// 			 어노테이션이 있어야 스프링이 이 클래스 안에 있는 코드들을 다른 메서드 실행 전/후에 끼워 넣을 준비를 합니다.
// @Component : 내가 만든 라이브러리

// @DistributedLock가 명시되어있는 메서드가 실행될 때, 실행을 가로채서 앞뒤로 락 로직을 끼워넣음

//DistributedLockAspect / RedisLockManager: 이건 **"도구"**입니다. 유저 락, 상품 락, 주문 락 등 어떤 도메인이든 "락이 필요할 때" 가져다 쓰는 공통 인프라 로직입니다. 

//20260313 MultiLock 적용

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {
	
	private final RedisLockManager redisLockManager; // 분리된 매니저 주입
	
	// ProceedingJoinPoint : aop의 핵심 인터페이스 중 하나로 가로챈 메서드의 실행 권한을 쥐고 있음
	
	//@DistributedLock 붙은 메서드가 실행될 때 그 동작을 가로채서(Intercept) 로직을 수행하게 됩니다.
	// ProceedingJoinPoint : 메서드 실행 경로
	// DistributedLock : 클래스 필드값
	
	// 감지프로세스
	// 1. 2번째 인자 DistributedLock distributedLock 확인
	// 2. @Around("@annotation(distributedLock)") 과 변수명 일치하는지 확인
	// 3. @com.example.demo.global.annotation.DistributedLock 경로가 나오고 DistributedLock 인터페이스 파일 기준으로 감지
    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
    	System.out.println("distributedLock:::" + distributedLock);
    	
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        
        // 1. SpEL을 통해 키 기반 값 획득 (단일 객체 혹은 List)
        Object keyBase = CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        log.info("keyBase:{}",keyBase);
        
        List<String> lockKeys = new ArrayList<>();
        String prefix = distributedLock.keyPrefix();
        
        if (keyBase instanceof List<?> keys) {
        	for (Object key : keys) {
        		// String.valueOf()를 사용하면 숫자형 ID도 안전하게 문자열로 바뀝니다.
                lockKeys.add(prefix + ":" + String.valueOf(key));
        	}
        	Collections.sort(lockKeys); // 리스트일 때만 정렬 (교착 상태 방지용)
        } else {
            // 단일 값 처리
        	lockKeys.add(prefix + ":" + String.valueOf(keyBase));
        }
        
        // 2. MultiLock 생성 (배열로 변환)
        List<RLock> lockList = new ArrayList<>();

        for (String key : lockKeys) {
            RLock lock = redisLockManager.getLock(key);
            lockList.add(lock);
        }
        
        RLock[] locks = lockList.toArray(new RLock[0]); // 리스트를 Redisson MultiLock에서 사용하는 배열(RLock[])로 변환
        
        RLock multiLock = redisLockManager.getMultiLock(locks); // redissonClient.getMultiLock(locks) 호출 필요
        
        try {
            // 3. 모든 락 획득 시도
            boolean available = multiLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) {
                return false; // 락 획득 실패 처리
            }

            // 비즈니스 로직 실행 (AOP의 경우 joinPoint.proceed() 호출)
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 획득 중 인터럽트 발생", e); 
        } finally {
        	// 직접 체크하지 말고 매니저의 메서드 호출
            redisLockManager.unlock(multiLock);
        }
        
        
//        // 1. CustomSpringELParser을 이용해서 키 생성
//        System.out.println("keyBase:::" + keyBase);
//        String key = distributedLock.keyPrefix() + ":" +String.valueOf(keyBase);
//        System.out.println("key:::" + key);
//        
//        // 2. RedisLockManager를 통해 락 객체 획득
//        RLock rLock = redisLockManager.getLock(key);
//        System.out.println("락 이름: " + rLock.getName());
//        System.out.println("현재 잠김 여부: " + rLock.isLocked());
//        System.out.println("내 스레드 점유 여부: " + rLock.isHeldByCurrentThread());
//        System.out.println("재진입 횟수: " + rLock.getHoldCount());
//
//        try {
//            // 3. 락 획득 시도
//        	
//        	// true:락획득, false:락획득실패
//            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
//            if (!available) return false;
//
//            // 4. 비즈니스 로직 실행
//            return joinPoint.proceed(); 
//        } finally {
//            // 5. 매니저를 통한 안전한 해제
//        	redisLockManager.unlock(rLock);
//        }
        
    }
}

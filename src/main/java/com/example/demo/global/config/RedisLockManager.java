package com.example.demo.global.config;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockManager {
	private final RedissonClient redissonClient;

    // Aspect에서 호출하는 메서드: Redisson의 RLock 객체를 반환합니다.
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    // 락을 하나로 묶는 MultiLock 생성
    public RLock getMultiLock(RLock[] locks) {
        return redissonClient.getMultiLock(locks);
    }

    // 락 해제 로직: 안전한 해제를 위해 체크 로직을 포함합니다.
    public void unlock(RLock lock) {
    	if (lock == null) return;

        try {
            // 1. 먼저 현재 쓰레드가 락을 가지고 있는지 확인 시도
            // (일반 RLock은 여기서 걸러져서 성능상 이점 + 불필요한 예외 방지)
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (UnsupportedOperationException e) {
            // 2. MultiLock의 경우 isHeldByCurrentThread() 호출 시 이 예외가 발생함
            // MultiLock은 그냥 바로 unlock() 시도 (내부에서 알아서 체크함)
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException ie) {
                // 이미 만료되었거나 소유권이 없는 경우 무시
            }
        } catch (IllegalMonitorStateException e) {
            // 일반 락인데 소유권이 없는 경우 무시
        }
    }
}

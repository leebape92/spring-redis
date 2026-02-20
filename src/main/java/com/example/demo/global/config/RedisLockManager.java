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

    // 락 해제 로직: 안전한 해제를 위해 체크 로직을 포함합니다.
    public void unlock(RLock lock) {
        try {
            // 락이 존재하고, 현재 쓰레드가 락을 점유하고 있을 때만 해제
            if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException e) {
            // 만료 시간 초과 등으로 이미 해제된 경우 예외가 발생할 수 있으므로 로그 처리
            // 별도의 throw는 하지 않음 (비즈니스 로직에 영향을 주지 않기 위함)
        }
    }
}

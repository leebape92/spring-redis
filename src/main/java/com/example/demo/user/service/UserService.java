package com.example.demo.user.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.redis.RedisService;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final RedissonClient redissonClient;
	
    private static final long CACHE_TTL = 300; // 5분
    private static final String LOCK_PREFIX = "lock:user:";
    
    private String userGetKey(Long userId) {
        return "user:" + userId;
    }

    // Create / Update
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
//        redisService.save(userGetKey(savedUser.getId()), savedUser, CACHE_TTL);
        ValueOperations<String, Object> ops = redisService.opsForValue();
        ops.set(userGetKey(savedUser.getId()), savedUser, CACHE_TTL, TimeUnit.SECONDS);

        
        System.out.println(redisService.getAllKeys());
        return savedUser;
    }

    // Read
    public User getUser(Long id) {
        String key = userGetKey(id);
        String lockKey = LOCK_PREFIX + id;
        // Redis 먼저 조회
//        Object cached = redisService.get(key);
//        System.out.println("cached ::: " + cached);
        
        // 캐시 조회
        Object cached = redisService.get(key);
        if (cached instanceof User) {
            return (User) cached;
        }
        
        // 분산 락 시도
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            // 최대 3초 대기, 5초 동안 락 유지(자동해제)
            isLocked = lock.tryLock(3, 5, TimeUnit.SECONDS);
            
            // 락 획득
            if (isLocked) {
                // 🔁 락 획득 후 다시 캐시 확인 (다른 스레드가 이미 채워놨을 수 있음)
                Object recheck = redisService.get(key);
                if (recheck instanceof User) {
                    return (User) recheck;
                }

                // 🔍 DB 조회
                User user = userRepository.findById(id).orElse(null);
                if (user != null) {
                    redisService.save(key, user, CACHE_TTL);
                }
                return user;
            } else {
                // 락을 얻지 못하면 캐시가 채워질 때까지 잠시 대기 후 재조회
                TimeUnit.MILLISECONDS.sleep(200);
                Object retry = redisService.get(key);
                return retry instanceof User ? (User) retry : null;
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
		} finally {
            // 락 해제
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }	
		}
        
//        if (cached instanceof User) { // cached 데이터가 User 타입인지 확인
//            return (User) cached;
//        }
//        // Redis 없으면 DB 조회 후 캐시 저장
//        User user = userRepository.findById(id).orElse(null);
//        System.out.println("usr ::: " + user);
//        if (user != null) {
//            redisService.save(key, user, CACHE_TTL);
//        }
//        System.out.println(redisService.getAllKeys());
//        return user;
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        redisService.delete(userGetKey(id));
    }
}

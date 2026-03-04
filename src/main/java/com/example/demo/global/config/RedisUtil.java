package com.example.demo.global.config;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
//    public void save(String key, Object value, long ttlSeconds) {
//    	System.out.println("key :::" + key);
//    	System.out.println("value :::" + value);
//    	System.out.println("ttlSeconds ::: " + ttlSeconds);
//        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
//    }
//    
//    public Object get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//    
//    // 단건조회
//    public ValueOperations<String, Object> opsForValue() {
//        return redisTemplate.opsForValue();
//    }
//    
//    // 다건조회
//    public ListOperations<String, Object> getListOperations() {
//        return redisTemplate.opsForList();
//    }
//
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public boolean exists(String key) {
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }
//    
//    public Set<String> getAllKeys() {
//        return redisTemplate.keys("*");
//    }
    
 // 1. 저장 (명칭 변경 및 TTL 자동 처리)
    public void set(String key, Object value, long ttlSeconds) {
        if (ttlSeconds > 0) {
            redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    // 2. 단건 조회
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 3. 존재 여부
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 4. 원자적 감소 (재고/쿠폰 필수 메서드)
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // 5. 원자적 증가 (조회수/재고 복구 필수 메서드)
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // 6. 삭제
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    // 7. 유효시간 설정/갱신
    public boolean expire(String key, long ttlSeconds) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, ttlSeconds, TimeUnit.SECONDS));
    }}

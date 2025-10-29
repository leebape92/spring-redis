package com.example.demo.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    //
    public static final String USER_KEY_PREFIX = "user:";

    public void save(String key, Object value, long ttlSeconds) {
    	System.out.println("key :::" + key);
    	System.out.println("value :::" + value);
    	System.out.println("ttlSeconds ::: " + ttlSeconds);
        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
    }
    
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    // 단건조회
    public ValueOperations<String, Object> opsForValue() {
        return redisTemplate.opsForValue();
    }
    
    // 다건조회
    public ListOperations<String, Object> getListOperations() {
        return redisTemplate.opsForList();
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }
}

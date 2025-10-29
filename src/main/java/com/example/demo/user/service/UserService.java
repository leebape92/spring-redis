package com.example.demo.user.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.redis.RedisService;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class UserService {

    private static final long CACHE_TTL = 1800; // 30분

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    private String userGetKey(Long userId) {
        return redisService.USER_KEY_PREFIX + userId;
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
        System.out.println("key ::: " + key);
        // Redis 먼저 조회
//        Object cached = redisService.get(key);
//        System.out.println("cached ::: " + cached);

        
        ValueOperations<String, Object> ops = redisService.opsForValue();
        Object cached = ops.get(userGetKey(id));
        
        System.out.println("cached ::: " + cached);
        
        if (cached instanceof User) { // cached 데이터가 User 타입인지 확인
            return (User) cached;
        }
        // Redis 없으면 DB 조회 후 캐시 저장
        User user = userRepository.findById(id).orElse(null);
        System.out.println("usr ::: " + user);
        if (user != null) {
            redisService.save(key, user, CACHE_TTL);
        }
        System.out.println(redisService.getAllKeys());
        return user;
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        redisService.delete(userGetKey(id));
    }
}

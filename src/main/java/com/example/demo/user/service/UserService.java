package com.example.demo.user.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.demo.global.annotation.DistributedLock;
import com.example.demo.redis.RedisService;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
    private final RedisService redisService;
    
    private static final long CACHE_TTL = 300; // 5분
    
    private String userGetKey(Long userId) {
        return "user:" + userId;
    }
    
    @DistributedLock(key = "#user.id", waitTime = 3, leaseTime = 5)
    public UserEntity saveUser(UserEntity userEntity) {
    	System.out.println("userEntity :::" + userEntity);
    	
    	// 1. DB 저장
    	UserEntity savedUser = userRepository.save(userEntity);
    	
    	// 2. 캐시 갱신
        redisService.opsForValue().set(userGetKey(savedUser.getId()), savedUser, CACHE_TTL, TimeUnit.SECONDS);
        
		return userEntity;
    	
    }
    
}

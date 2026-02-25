package com.example.demo.domain.user.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.annotation.DistributedLock;
import com.example.demo.global.config.RedisService;

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
    
    // @DistributedLock(key = "#userEntity 는 saveUser 의 변수명 사용해야함
    @DistributedLock(key = "#userEntity.email", waitTime = 3, leaseTime = 5)
    public UserEntity saveUser(UserEntity userEntity) {
    	System.out.println("userEntity :::" + userEntity);
    	
        
    	// 1. 락을 잡은 상태에서 중복 여부 확인
        boolean exists = userRepository.existsByEmail(userEntity.getEmail());
        if (exists) {
            System.out.println("중복된 이메일입니다: " + userEntity.getEmail());
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
    	
    	// 2. 존재하지 않을 때만 저장
    	UserEntity savedUser = userRepository.save(userEntity);
    	
    	// 3. 캐시 갱신 : DB에 저장된 최신 데이터를 레디스 메모리에 복사해두는 역활
    	// key-value저장 ex)set(id값, 저장데이터, 유효시간, CACHE_TTL 초 명시)
        redisService.opsForValue().set(userGetKey(savedUser.getId()), savedUser, CACHE_TTL, TimeUnit.SECONDS);
        
        Object cachedData = redisService.opsForValue().get(userGetKey(savedUser.getId()));
        System.out.println("cachedData:::" + cachedData);
        
		return userEntity;
    	
    }
    
}

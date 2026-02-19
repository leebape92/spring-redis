package com.example.demo.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//Redis는 데이터를 담아두는 서버 그 자체입니다.
//Redisson은 Java 환경에서 그 서버를 가장 똑똑하고 편하게 쓰기 위한 도구입니다.

// @Configuration : 설정을 위한 클래스 외부 라이브러리 설정이나 복잡한 초기화 로직에 사용


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // key 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // value 직렬화 제이슨
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 기본 직렬화
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        
        // 모든 설정 적용 초기화
        template.afterPropertiesSet();
        
        return template;
    }
    
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://localhost:6379")
              .setPassword("mypassword")
              .setDatabase(0);
        return Redisson.create(config);
    }
}


//설정파일 관련

//레디스 저장 코드
//redisService.save(userGetKey(savedUser.getId()), savedUser, CACHE_TTL);

//public void save(String key, Object value, long ttlSeconds) {
//    redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
//}

// key > user:1
// value > User(id=2, name=test21, age=21)
// ttlSeconds > 1800
// RedisTemplate의 ValueOperations를 사용해서 Redis에 저장

//RedisConfig 에서 지정한 직렬화 규칙을 따라서 저장
//template.setKeySerializer(new StringRedisSerializer()); // 문자열 직렬화 > "user:1"
//template.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 제이슨 직렬화 > { "id":1, "name":"test", "age":11 }

//package com.example.demo.user.service;
//
//import java.util.concurrent.TimeUnit;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.redis.RedisService;
//import com.example.demo.user.entity.User;
//import com.example.demo.user.repository.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class UserService_back {
//
//    private final UserRepository userRepository;
//    private final RedisService redisService;
//    private final RedissonClient redissonClient;
//	
//    private static final long CACHE_TTL = 300; // 5분
//    private static final String LOCK_PREFIX = "lock:user:";
//    
//    private String userGetKey(Long userId) {
//        return "user:" + userId;
//    }
//
//    public User saveUser(User user) {
//        String lockKey = LOCK_PREFIX + user.getId();
//        RLock lock = redissonClient.getLock(lockKey);
//        boolean isLocked = false;
//
//        try {
//        	// 다른 스레드가 해당 키에 대한 락을 가지고 있다면 대기상태로 
//            // 3초 동안 다른 스레드가 락을 풀기를 기다림 3초 안에 락을 얻으면 true, 얻지 못하면 false
//        	// 락을 획득하면 Redisson이 자동흐로 5초 후 락을 해제, unlock() 호출 하지 않아도 자동 해제 finally 블록에서 lock.unlock() 해제하는것이 안전
//            isLocked = lock.tryLock(3, 5, TimeUnit.SECONDS);
//            if (isLocked) {
//                // DB 저장
//                User savedUser = userRepository.save(user);
//
//                // 캐시 갱신 (opsForValue 사용 가능) increment, multiGet, setIfAbsent 필요하면 사용
//                // 단순 캐시 저장이라면 save 함수 사용해도 무관
//                ValueOperations<String, Object> ops = redisService.opsForValue();
//                ops.set(userGetKey(savedUser.getId()), savedUser, CACHE_TTL, TimeUnit.SECONDS);
//
//                System.out.println("ops :::" + ops);
//                
//                return savedUser;
//            } else {
//                // 락을 얻지 못한 경우 잠시 대기 후 캐시 재확인 (optional)
//                TimeUnit.MILLISECONDS.sleep(200);
//                Object cached = redisService.get(userGetKey(user.getId()));
//                return cached instanceof User ? (User) cached : null;
//            }
//
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            return null;
//
//        } finally {
//            // 락 해제 (현재 스레드가 소유한 경우에만)
//            if (isLocked && lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
//
//    // Read
//    public User getUser(Long id) {
//        String key = userGetKey(id);
//        String lockKey = LOCK_PREFIX + id;
//        // Redis 먼저 조회
////        Object cached = redisService.get(key);
////        System.out.println("cached ::: " + cached);
//        
//        // 캐시 조회
//        Object cached = redisService.get(key);
//        if (cached instanceof User) {
//            return (User) cached;
//        }
//        
//        // 분산 락 시도
//        RLock lock = redissonClient.getLock(lockKey);
//        System.out.println("lock:::" + lock);
//        boolean isLocked = false;
//        try {
//            // 최대 3초 대기, 5초 동안 락 유지(자동해제)
//            isLocked = lock.tryLock(3, 5, TimeUnit.SECONDS);
//            
//            // 락 획득
//            if (isLocked) {
//                // 🔁 락 획득 후 다시 캐시 확인 (다른 스레드가 이미 채워놨을 수 있음)
//                Object recheck = redisService.get(key);
//                if (recheck instanceof User) {
//                    return (User) recheck;
//                }
//
//                // 🔍 DB 조회
//                User user = userRepository.findById(id).orElse(null);
//                if (user != null) {
//                    redisService.save(key, user, CACHE_TTL);
//                }
//                return user;
//            } else {
//                // 락을 얻지 못하면 캐시가 채워질 때까지 잠시 대기 후 재조회
//                TimeUnit.MILLISECONDS.sleep(200);
//                Object retry = redisService.get(key);
//                return retry instanceof User ? (User) retry : null;
//            }
//            
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            return null;
//		} finally {
//            // 락 해제
//            if (isLocked && lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }	
//		}
//    }
//
//    // Delete
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//        redisService.delete(userGetKey(id));
//    }
//}

package com.example.demo.example;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    public void test() {
        // 이제 로그 코드는 신경 쓰지 않고 주문 로직에만 집중합니다.
        System.out.println("오더서비스 주문 로직 실행 중...");
    }
}

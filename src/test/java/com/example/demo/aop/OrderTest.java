package com.example.demo.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.example.OrderService;

@SpringBootTest
public class OrderTest {
	
	@Autowired
    private OrderService orderService;
	
	@Test
	@DisplayName("AOP 테스트!")
    public void order() {
        
		// 오더서비스 호출
		orderService.order();
		
        System.out.println("끝...");
    }
	
}

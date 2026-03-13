package com.example.demo.order;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.order.dto.OrderCreateRequestDto;
import com.example.demo.domain.order.dto.OrderItemRequestDto;
import com.example.demo.domain.order.service.OrderCreateFacade;

@SpringBootTest
public class OrderSaveTest {

	@Autowired
	private OrderCreateFacade orderCreateFacade;
	
	@Test
	void OrderCreate() {
		
		// [Given] 1. 주문할 상품 리스트 생성 (상품 A, 상품 B)
		OrderItemRequestDto item1 = OrderItemRequestDto.builder()
                .productId(1L)
                .orderQuantity(2)
                .orderPrice(new BigDecimal("10000")) // 단가 1만 원
                .build();

        OrderItemRequestDto item2 = OrderItemRequestDto.builder()
                .productId(3L)
                .orderQuantity(1)
                .orderPrice(new BigDecimal("20000")) // 단가 5만 원
                .build();
		
		
        // 2. 전체 주문 요청 DTO 조립
        OrderCreateRequestDto orderCreateRequestDto = OrderCreateRequestDto.builder()
                .orderItemRequests(List.of(item1, item2)) // 상품 2종류 투입
                .status(10)
                .build();
        
        
        System.out.println("orderCreateRequestDto :::" + orderCreateRequestDto);
        
        // [When] 주문 실행
        orderCreateFacade.createOrder(orderCreateRequestDto);
	}

	
}

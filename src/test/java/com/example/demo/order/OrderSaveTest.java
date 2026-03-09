package com.example.demo.order;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.order.dto.OrderCreateRequestDto;
import com.example.demo.domain.order.service.OrderCreateFacade;

@SpringBootTest
public class OrderSaveTest {

	@Autowired
	private OrderCreateFacade orderCreateFacade;
	
	@Test
	void OrderCreate() {
		
		OrderCreateRequestDto orderCreateRequestDto = OrderCreateRequestDto.builder()
				.productId(1L)
				.quantity(5)
				.totalPrice(new BigDecimal("30000"))
				.status(10)
				.build();
		
		
		// 3. 실행 및 검증
//	    assertDoesNotThrow(() -> {
	        orderCreateFacade.createOrder(orderCreateRequestDto);
//	    });
//		orderCreateFacade.createOrder(orderCreateRequestDto);
		
	}

	
}

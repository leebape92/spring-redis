package com.example.demo.domain.order.service;

import org.springframework.stereotype.Component;

import com.example.demo.domain.order.dto.OrderCreateRequestDto;
import com.example.demo.global.annotation.DistributedLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateFacade {

    private final OrderService orderService;

    @DistributedLock(
            keyPrefix = "STOCK",
            key = "#orderCreateRequestDto.orderItemRequests.![productId]",
            waitTime = 10,
            leaseTime = 5
    )
    public void createOrder(OrderCreateRequestDto orderCreateRequestDto) {
    	log.info("orderCreateRequestDto:::",orderCreateRequestDto);
        // 핵심: 하나의 트랜잭션 안에서 재고 차감과 주문 생성이 모두 일어나야 함
        orderService.processOrder(orderCreateRequestDto);
    }
    
}

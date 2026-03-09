package com.example.demo.domain.order.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.order.dto.OrderCreateRequestDto;
import com.example.demo.domain.order.entity.OrderEntity;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.stock.entity.StockEntity;
import com.example.demo.domain.stock.repository.StockRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    
    
    // 모든 로직을 하나의 @Transactional 메서드에 넣으면, 재고 차감과 주문 저장이 모두 성공하거나 모두 실패하게 됩니다.
    @Transactional
    public void processOrder(OrderCreateRequestDto orderCreateRequestDto) {
    	System.out.println("orderCreateRequestDto:::" + orderCreateRequestDto);
        // 1. 재고 엔티티 조회 (락이 걸려있으므로 안전함)
        StockEntity stockEntity = stockRepository.findByProductId(orderCreateRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("재고 정보 없음"));

        // 2. 재고 차감
        stockEntity.decrease(orderCreateRequestDto.getQuantity());

        // 3. 주문서 생성 및 저장
        OrderEntity orderEntity = orderCreateRequestDto.toOrderEntity();
        orderRepository.save(orderEntity);
        
        // 트랜잭션 종료 시 Dirty Checking으로 재고 변경분 DB 반영 & 주문 저장 완료
    }
}

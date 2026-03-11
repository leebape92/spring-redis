package com.example.demo.domain.order.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.order.dto.OrderCreateRequestDto;
import com.example.demo.domain.order.dto.OrderItemRequestDto;
import com.example.demo.domain.order.entity.OrderEntity;
import com.example.demo.domain.order.entity.OrderItemEntity;
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
//    	System.out.println("orderCreateRequestDto:::" + orderCreateRequestDto);
//        // 1. 재고 엔티티 조회 (락이 걸려있으므로 안전함)
//        StockEntity stockEntity = stockRepository.findByProductId(orderCreateRequestDto.getProductId())
//                .orElseThrow(() -> new EntityNotFoundException("재고 정보 없음"));
//
//        // 2. 재고 차감
//        // Dirty Checking : @Transactional 메서드가 끝나는 시점에서 기존 DB의 상태 값이 다르면 자동으로 UPDATE
//        stockEntity.decrease(orderCreateRequestDto.getOrderQuantity());
//
//        // 3. OrderItemEntity 생성(주문 상세)
//        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
//        		.productId(orderCreateRequestDto.getProductId())
//        		.orderQuantity(orderCreateRequestDto.getOrderQuantity())
//        		.orderPrice(orderCreateRequestDto.getOrderPrice())
//        		.build();
//        
//        // 4. OrderEntity 생성(주문 마스터)
//        OrderEntity orderEntity = OrderEntity.builder()
//        		.status(10)
//        		.build();
//        
//        // 1:N 관계 맺기 (여기서 OrderEntity의 totalAmount가 다시 계산됨)
//        orderEntity.addOrderItem(orderItemEntity);
//        
//        //5. 주문서 생성
//        orderRepository.save(orderEntity);
    	
    	// 1. 주문 마스터(OrderEntity) 먼저 생성
        OrderEntity orderEntity = OrderEntity.builder()
                .status(10)
                .build();
        
        for(OrderItemRequestDto orderItemRequestDto : orderCreateRequestDto.getOrderItemRequests()) {
        	// (1) 각 상품별 재고 조회 및 차감
            StockEntity stockEntity = stockRepository.findByProductId(orderItemRequestDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("재고 없음: " + orderItemRequestDto.getProductId()));
            
            stockEntity.decrease(orderItemRequestDto.getOrderQuantity());
            
            // (2) 각 상품별 OrderItemEntity 생성
            OrderItemEntity orderItemEntity = OrderItemEntity.builder()
            		.productId(orderItemRequestDto.getProductId())
            		.orderQuantity(orderItemRequestDto.getOrderQuantity())
            		.orderPrice(orderItemRequestDto.getOrderPrice())
            		.build();
            
            // (3) 주문 마스터에 상세 항목 추가
            orderEntity.addOrderItem(orderItemEntity);
            
        }
        
        // 3. 마지막에 한 번만 저장 (Cascade에 의해 모든 Item이 함께 저장됨)
        orderRepository.save(orderEntity);
        
    }
    
    // 로직 처리 순서 트랜잭션 커밋 시점
    // 1. 트랜잭션이 끝났으니 변경된 내용이 있는지 검사
    // 2. Dirty Checking StockEntity의 quantity값이 변경 감지
    //    UPDATE 쿼리문 생성 : 엔티티의 @Id 값을 키로 사용하여
    // 3. orderRepository.save() INSERT 쿼리 생성
    // 4. 위 쿼리들을 한꺼번에 실행하고 커밋 완료
    
    // ex) 주문수량 5가 들어와서 decrease 함수에서 -5가 되어도 호출이후에 조회함수를 입력해도 JPA의 쓰기 지연(Write-Behind)으로 인해서
    // 트랜잭션이 완전히 종료된 시점에 정상적으로 -5가 된것을 확인가능하다
    
    
    
    // ----------------------------------------------------
    // TB_ORDER_ITEM 테이블에 저장되는 이유
    // 핵심은 OrderEntity 상단에 선언한 cascade = CascadeType.ALL 옵션입니다.
//    Cascade(영속성 전이): "나(Order)가 저장될 때, 내 리스트에 들어있는 녀석들(OrderItem)도 같이 저장해줘!"라는 뜻입니다.
//    작동 순서:
//    서비스에서 orderRepository.save(orderEntity)를 호출합니다.
//    JPA는 orderEntity를 분석하다가 orderItems 리스트 안에 데이터가 있는 것을 발견합니다.
//    CascadeType.ALL 설정을 확인하고, 리스트 안의 모든 OrderItemEntity를 꺼내서 자동으로 INSERT 쿼리를 생성합니
    
}

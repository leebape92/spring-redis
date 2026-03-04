package com.example.demo.domain.stock.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

//	private final StockRepository stockRepository;
//	
//    @Transactional // 부모(Facade)에서 시작된 트랜잭션이 있다면 그에 합류함
//    public void decreaseStock(Long productId, Integer quantity) {
//        // 1. 재고 엔티티 조회
//        StockEntity stock = stockRepository.findByProductId(productId)
//                .orElseThrow(() -> new EntityNotFoundException("해당 상품의 재고 정보가 없습니다."));
//
//        // 2. 엔티티에 차감 명령 (내부에서 검증 후 수량 변경)
//        stock.decrease(quantity);
//
//        // 3. Dirty Checking에 의해 메서드 종료(트랜잭션 커밋) 시 DB에 반영됨
//    }
}

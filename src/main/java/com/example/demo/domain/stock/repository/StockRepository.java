package com.example.demo.domain.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.stock.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
	
	// productId 필드를 기준으로 조회하며, 결과는 Optional로 감싸서 반환
	Optional<StockEntity> findByProductId(Long productId);

}

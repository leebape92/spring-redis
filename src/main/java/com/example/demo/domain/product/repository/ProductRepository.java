package com.example.demo.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
package com.example.demo.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
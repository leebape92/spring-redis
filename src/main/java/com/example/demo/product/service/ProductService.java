package com.example.demo.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     */
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = ProductEntity.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .status(productDTO.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductEntity saved = productRepository.save(productEntity);
        return ProductDTO.fromProductDTOEntity(saved);
    }

    /**
     * 상품 전체 조회
     */
    public List<ProductDTO> getListProduct(ProductDTO productDTO) {
        // DB에서 전체 상품 조회
        List<ProductEntity> ProductEntityList = productRepository.findAll();

        // Entity → DTO 변환
        List<ProductDTO> ProductDTOList = ProductEntityList.stream()
                .map(ProductDTO::fromProductDTOEntity) // DTO 내부 정적 메서드 사용
                .collect(Collectors.toList());

        // 변환된 리스트 반환
        return ProductDTOList;
    }

    /**
     * 상품 단건 조회
     */
    public ProductDTO getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));
        return convertToDTO(product);
    }

    /**
     * 상품 수정
     */
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setStatus(dto.getStatus() != null ? dto.getStatus() : product.getStatus());
        product.setUpdatedAt(LocalDateTime.now());

        ProductEntity updated = productRepository.save(product);
        return convertToDTO(updated);
    }

    /**
     * 상품 삭제
     */
    public void deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));
        productRepository.delete(product);
    }

    /**
     * Entity → DTO 변환
     */
    private ProductDTO convertToDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

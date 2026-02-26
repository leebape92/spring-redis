package com.example.demo.domain.product.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.product.dto.ProductSaveRequestDto;
import com.example.demo.domain.product.entity.ProductEntity;
import com.example.demo.domain.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void createProduct(ProductSaveRequestDto productSaveRequestDto) {
    	
    	ProductEntity couponEntity = productSaveRequestDto.toProductEntity();
    	
    	productRepository.save(couponEntity);
    	
    }

//    /**
//     * 상품 전체 조회
//     */
//    public List<ProductDto> getListProduct(ProductDto productDTO) {
//        // DB에서 전체 상품 조회
//        List<ProductEntity> ProductEntityList = productRepository.findAll();
//
//        // Entity → DTO 변환
//        List<ProductDto> ProductDTOList = ProductEntityList.stream()
//                .map(ProductDto::fromProductDTOEntity) // DTO 내부 정적 메서드 사용
//                .collect(Collectors.toList());
//
//        // 변환된 리스트 반환
//        return ProductDTOList;
//    }
//
//    /**
//     * 상품 단건 조회
//     */
//    public ProductDto getProductById(Long id) {
//        ProductEntity product = productRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));
//        return convertToDTO(product);
//    }
//
//    /**
//     * 상품 수정
//     */
//    public ProductDto updateProduct(Long id, ProductDto dto) {
//        ProductEntity product = productRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));
//
//        product.setName(dto.getName());
//        product.setDescription(dto.getDescription());
//        product.setPrice(dto.getPrice());
//        product.setStockQuantity(dto.getStockQuantity());
//        product.setStatus(dto.getStatus() != null ? dto.getStatus() : product.getStatus());
//        product.setUpdatedAt(LocalDateTime.now());
//
//        ProductEntity updated = productRepository.save(product);
//        return convertToDTO(updated);
//    }
//
//    /**
//     * 상품 삭제
//     */
//    public void deleteProduct(Long id) {
//        ProductEntity product = productRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + id));
//        productRepository.delete(product);
//    }
//
//    /**
//     * Entity → DTO 변환
//     */
//    private ProductDto convertToDTO(ProductEntity entity) {
//        return ProductDto.builder()
//                .id(entity.getId())
//                .name(entity.getName())
//                .description(entity.getDescription())
//                .price(entity.getPrice())
//                .stockQuantity(entity.getStockQuantity())
//                .status(entity.getStatus())
//                .createdAt(entity.getCreatedAt())
//                .updatedAt(entity.getUpdatedAt())
//                .build();
//    }
}

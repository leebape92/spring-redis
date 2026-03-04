package com.example.demo.domain.product.dto;

import java.math.BigDecimal;

import com.example.demo.domain.product.entity.ProductEntity;
import com.example.demo.domain.stock.entity.StockEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @NoArgsConstructor(access = AccessLevel.PROTECTED) : 매개변수가 없는 "기본 생성자"를 만들되, 접근 범위를 protected로 제한
// -> 같은 패키지에 있거나 상속받은 클래스가 아니라면, 외부(예: 다른 패키지의 컨트롤러나 서비스)에서 new ProductSaveRequestDto()를 호출해 객체를 생성하는 것이 금지됩니다.
// * 왜 이렇게 설정되어 있나요? (객체 지향적 관점)
// 보통 DTO나 Entity에서 기본 생성자를 PROTECTED로 막는 이유는 **"불완전한 객체 생성을 방지"**하기 위해서입니다.
// 데이터가 하나도 없는 빈 객체를 new로 생성해서 사용하는 대신, 전체 필드를 채우는 생성자나 **빌더(Builder)**를 통해서만 값을 넣으며 객체를 만들도록 강제하는 것이죠.
// 다만, JPA나 Jackson(JSON 변환 라이브러리) 같은 프레임워크는 내부적으로 기본 생성자가 필요하기 때문에 아예 삭제하지는 않고 PROTECTED로 열어두는 것이 관례입니다.

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateRequestDto {
	
    private String productName;
    private String description;
    private BigDecimal productPrice;
    private Integer quantity;
    private Integer status; // 10:판매중, 20:판매완료, 30:상품준비중, 40:판매중지, 50:삭제
    
    
    @Builder
	public ProductCreateRequestDto(String productName, String description, 
			BigDecimal productPrice, Integer quantity, Integer status) {
		this.productName = productName;
		this.description = description;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.status = status;
	}
    
    
    // 상품 엔티티 변환
    public ProductEntity toProductEntity() {
    	// 빌더 결과를 변수에 할당
    	ProductEntity productEntity = ProductEntity.builder()
                .productName(this.productName)
                .description(this.description)
                .productPrice(this.productPrice)
                .status(this.status)
                // createdAt, updatedAt : 기본생성자 builder productEntity에서 관리
                .build();
    	
    	System.out.println("productEntity ::: " + productEntity);

        // 변수 반환
        return productEntity;
    }
    
    // 재고 엔티티 변환
    public StockEntity toStockEntity(Long productId) {
    	StockEntity stockEntity = StockEntity.builder()
                .productId(productId)
                .quantity(this.quantity)
                .build();
		return stockEntity;
    }
    



//    “ProductDTO가 Entity로부터 생성된다”
    // Entity로 부터 PrdouctDTO 생성
//    public static ProductSaveRequestDto fromProductDrecordTOEntity(ProductEntity productEntity) {
//        return ProductSaveRequestDto.builder()
//                .id(productEntity.getId())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .stockQuantity(productEntity.getStockQuantity())
//                .status(productEntity.getStatus())
//                .createdAt(productEntity.getCreatedAt())
//                .updatedAt(productEntity.getUpdatedAt())
//                .build();
//    }
    
    
    
}


//package com.example.demo.product;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.demo.domain.product.dto.ProductCreateRequestDto;
//import com.example.demo.domain.product.service.ProductService;
//
//@SpringBootTest
//public class ProductSaveTest {
//
//	@Autowired
//    private ProductService productService;
//	
//	@Test
//	void ProductCreate() {
//		
//		
//		// new 생성자의 단점 필드가 많아질수록 인자값이 어떤 필드에 들어가는지 ProductSaveRequestDto클래스에 들어가서 확인하지 않는 이상 어려움
//		// ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto("상품명", "설명", new BigDecimal("10000"), 100, 100, "SALE");
//		
//		// Builder 패턴 장점 인자 순서 상관없음, 필요한 것만 넣기 가능!
//		ProductCreateRequestDto productSaveRequestDto = ProductCreateRequestDto.builder()
//			    .productName("마우스")
//			    .description("마우스설명...")
//			    .productPrice(new BigDecimal("20000"))
//			    .status(10)
//			    .quantity(200)
//			    .build(); 
//        try {
//        	productService.createProduct(productSaveRequestDto);
//        } catch (Exception e) {
//        	System.out.println("e ::: " + e);
//		}
//		
//		
//		
//	}
//}

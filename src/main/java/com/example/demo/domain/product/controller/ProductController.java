//package com.example.demo.domain.product.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.domain.product.dto.ProductDto;
//import com.example.demo.domain.product.service.ProductService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/products")
//@RequiredArgsConstructor
//public class ProductController {
//
//    private final ProductService productService;
//    
//    // CRUD !!!!
//    
//    // 상품 등록
//    @PostMapping
//    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDTO) {
//    	
//    	ProductDto result = productService.createProduct(productDTO);
//    	
//        return ResponseEntity.ok(result);
//    }
//
//    // 상품 전체 조회
//    @GetMapping
//    public ResponseEntity<List<ProductDto>> getListProduct(@RequestBody ProductDto productDTO) {
//    	
//    	List<ProductDto> result = productService.getListProduct(productDTO);
//    	
//        return ResponseEntity.ok(result);
//    }
//
//    // 상품 단건 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }
//
//    // 상품 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDTO) {
//        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
//    }
//
//    // 상품 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//}

package com.javalab.board.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.javalab.board.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	// 카테고리ID로 상품 조회
	Page<Product> findProductByCategoryCategoryNo(Integer categoryId, Pageable pageable);
	
	// 상품 이름 특정 키워드에 맞는 상품 조회
    List<Product> findByProductNameContaining(String searchKeyword);
    
    // 페이징 검색기능
    Page<Product> findByproductNameContaining(String searchKeyword, Pageable pageable);
    
    Product findByProductNo(int productNo);




}
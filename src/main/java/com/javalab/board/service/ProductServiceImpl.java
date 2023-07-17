package com.javalab.board.service;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javalab.board.dto.ProductDTO;
import com.javalab.board.entity.Product;
import com.javalab.board.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {
   private final ProductRepository productRepository;
   


   public ProductServiceImpl(ProductRepository productRepository) {
      this.productRepository = productRepository;
   }
   
   // 검색기능
   @Override
	public Page<Product> productSearchList(String searchKeyword, Pageable pageable){
		return productRepository.findByproductNameContaining(searchKeyword, pageable);
	}

   
  
	// 상품리스트 (페이징기능포함)
	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	
   
   
   // 상품확인
   @Override
   public Product getProductById(int productId) {
      return productRepository.findById(productId).orElse(null);
   }
   
   @Override
   public ProductDTO getProductDetail(Integer productNo) {
      Optional<Product> entity = productRepository.findById(productNo);
      return entity.isPresent() ? entityToDto(entity.get()) : null;
   }
   
   
   // 상품저장
      @Override
      public Product saveProduct(Product product) throws Exception {
          
    	  return productRepository.save(product);
      }

            
      @Override
      public byte[] getProductImage(int productNo, int imageIndex) throws Exception {
    	    Product product = productRepository.findByProductNo(productNo);
    	    byte[] imageData = null;
    	    if (product != null) {
    	        switch (imageIndex) {
    	            case 1:
    	                imageData = product.getProductImage1();
    	                break;
    	            case 2:
    	                imageData = product.getProductImage2();
    	                break;
    	            case 3:
    	                imageData = product.getProductImage3();
    	                break;
    	            default:
    	                break;
    	        }
    	    }
    	    return imageData;
    	}

   
   // 상품 삭제
   @Override
   public void deleteProduct(int productId) {
      productRepository.deleteById(productId);
   }
   
   
   
   // 상품 업데이트
    @Override
    public void updateProduct(Product product) {
           Product existingProduct = getProductById(product.getProductNo());
           // Update the existing product with the new details
           existingProduct.setCategory(product.getCategory());
           existingProduct.setProductName(product.getProductName()); 
           existingProduct.setProductPrice(product.getProductPrice());
           existingProduct.setProductDescription(product.getProductDescription());
           existingProduct.setProductImage1(product.getProductImage1());
           existingProduct.setProductImage2(product.getProductImage2());
           existingProduct.setProductImage3(product.getProductImage3());
        
           
           // Save the updated product
           productRepository.save(existingProduct);
       }
   
    
    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .productNo(product.getProductNo())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .productImage1(product.getProductImage1())
                .productImage2(product.getProductImage2())
                .productImage3(product.getProductImage3())
                .category(product.getCategory())
                .basketProducts(product.getBasketProducts())
                .orderProducts(product.getOrderProducts())
                .build();
    }
    
    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .productNo(productDTO.getProductNo())
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .productPrice(productDTO.getProductPrice())
                .productImage1(productDTO.getProductImage1())
                .productImage2(productDTO.getProductImage2())
                .productImage3(productDTO.getProductImage3())
                .category(productDTO.getCategory())
                .basketProducts(productDTO.getBasketProducts())
                .orderProducts(productDTO.getOrderProducts())
                .build();
    }
    

    
    // 모든 품목 조회
    @Override
	public List<Product> mainAllProducts() {
		return productRepository.findAll();
	}

    // 카테고리 품목별 조회
	@Override
	public Page<Product> getAllProductsByCategory(Integer categoryId, Pageable pageable) {
		Page<Product> products = productRepository.findProductByCategoryCategoryNo(categoryId, pageable);
		return products;
	}
	
	
	

}
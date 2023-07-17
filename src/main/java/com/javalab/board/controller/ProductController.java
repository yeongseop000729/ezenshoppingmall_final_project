package com.javalab.board.controller;

import com.javalab.board.dto.BasketDTO;




import com.javalab.board.dto.BasketProductDTO;
import com.javalab.board.dto.ProductDTO;
import com.javalab.board.entity.Category;
import com.javalab.board.entity.Product;
import com.javalab.board.service.CategoryService;
import com.javalab.board.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private CategoryService categoryService;
    
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // 상품목록 가져오기
    @GetMapping
    public String getAllProducts(Model model, 
    		@PageableDefault(page=0, size=6, sort="productNo", direction = Sort.Direction.DESC) Pageable pageable,
    		String searchKeyword) {
    	 
    	List<Category> categories = categoryService.getAllCategories();
    	Page<Product> products = null;
    	
    	if(searchKeyword == null) {
    		products = productService.getAllProducts(pageable);
    	} else {
    		products = productService.productSearchList(searchKeyword, pageable);
    	}
    	
        
       
        int nowPage = products.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, products.getTotalPages());
        
        model.addAttribute("products", products);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categories", categories);
        
      
        return "product/allProducts";
    }
    
    
    
    // 상품 상세보기
    @GetMapping("/{productNo}")
    public String productDetail(@PathVariable Integer productNo, Model model, BasketDTO basketDTO, BasketProductDTO basketProductDTO) {
       ProductDTO product = productService.getProductDetail(productNo);
       model.addAttribute("product", product);
       return "product/productDetails";
    }
    
    // 상품 추가 페이지로 이동 어드민만
    @GetMapping("/add")
    public String showAddProductForm(Model model, HttpSession session) {
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (isAdmin) {
            model.addAttribute("product", new Product());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/addProduct";
        } else {
            // Handle the case when the user is not an admin (e.g., show an error page, redirect to a different page)
            return "redirect:/products";
        }
    }

    // 상품추가
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("categoryId") int categoryId, 
    		   @RequestParam("image1") MultipartFile image1,
               @RequestParam("image2") MultipartFile image2,
               @RequestParam("image3") MultipartFile image3) throws Exception {
   
            byte[] image1Data = image1.getBytes();
            byte[] image2Data = image2.getBytes();
            byte[] image3Data = image3.getBytes();
            
            product.setProductImage1(image1Data);
            product.setProductImage2(image2Data);
            product.setProductImage3(image3Data);
        
     
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        productService.saveProduct(product);
       
       
        
        return "redirect:/products";
    }
    
    
    @GetMapping("/image/{productNo}/{imageIndex}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productNo, @PathVariable int imageIndex) {
        try {
            byte[] imageData = productService.getProductImage(productNo, imageIndex);
            if (imageData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);  // 이미지 타입에 맞게 설정
                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // 상품 수정 페이지로 이동
    @GetMapping("/{productId}/edit")
    public String showEditProductForm(@PathVariable int productId, Model model) {
        Product product = productService.getProductById(productId);
        List<Category> categories = categoryService.getAllCategories(); // Retrieve the category list
        model.addAttribute("product", product);
        model.addAttribute("categories", categories); // Add the category list to the model
        return "product/editProduct";
    }
    
    // 상품수정기능
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product updatedProduct,  
 		   @RequestParam("image1") MultipartFile image1,
           @RequestParam("image2") MultipartFile image2,
           @RequestParam("image3") MultipartFile image3) throws IOException {
    	
    	
    	 byte[] image1Data = image1.getBytes();
         byte[] image2Data = image2.getBytes();
         byte[] image3Data = image3.getBytes();
         
         updatedProduct.setProductImage1(image1Data);
         updatedProduct.setProductImage2(image2Data);
         updatedProduct.setProductImage3(image3Data);
         
    	
        productService.updateProduct(updatedProduct);
       
    
        
        return "redirect:/products";
    }

    // 상품 삭제
    @PostMapping("/{productId}/delete")
    public String deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
    
   
}
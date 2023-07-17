package com.javalab.board.controller;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javalab.board.entity.Product;
import com.javalab.board.service.ProductService;


@Controller
public class HomeController {
	@Autowired
	 private ProductService productService;

   // 어플리케이션 처음 구동시 호출되는 메소드("/")
	@GetMapping("/")
   public String Main(Model model, Pageable pageable) {
   	
   	Page<Product> products = productService.getAllProducts(pageable);
       
       model.addAttribute("products", products);
   	
       return "main/main";	//
   }
    // 이용안내	
    @GetMapping("/guide") 
    public String guide() {
    	return "main/guide";
    }
    // 이용약관
    @GetMapping("/terms")
    public String terms() {
    	return "main/terms";
    }
    // 개인정보처리방침
    @GetMapping("/policy")
    public String policy() {
    	return "main/policy";
    }


}

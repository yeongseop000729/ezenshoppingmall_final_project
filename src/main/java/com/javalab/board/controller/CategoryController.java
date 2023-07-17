package com.javalab.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.javalab.board.entity.Category;
import com.javalab.board.entity.Product;
import com.javalab.board.service.CategoryService;
import com.javalab.board.service.ProductService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;
    
    
    // 전체 카테고리 리스트
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/listCategory";
    }

    @GetMapping("/products/{categoryId}")
    public String getProducts(@PathVariable Integer categoryId, Model model, Pageable pageable) {
    	
    	
    	Page<Product> products = productService.getAllProductsByCategory(categoryId, pageable);
    	model.addAttribute("products", products);
    	
    	int nowPage = products.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, products.getTotalPages());
        
        model.addAttribute("products", products);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        
    	
    	
        return "product/allProducts";
    }
    
    // 추가창 이동
    @GetMapping("/add")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/addCategory";
    }

    
    // 추가창 기능
    @PostMapping("/add")
    public String createCategory(@ModelAttribute("category") Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    
    
    // 수정창 이동
    @GetMapping("/{categoryId}/edit")
    public String showEditCategoryForm(@PathVariable int categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            model.addAttribute("category", category);
            return "category/editCategory";
        } else {
            return "redirect:/categories";
        }
    }
    
    
    // 수정 기능
    @PostMapping("/{categoryId}/edit")
    public String updateCategory(@PathVariable int categoryId, @ModelAttribute("category") Category updatedCategory) {
        Category existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory != null) {
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            // 필요에 따라 다른 필드들도 업데이트할 수 있습니다.

            Category savedCategory = categoryService.saveCategory(existingCategory);
            return "redirect:/categories";
        } else {
            return "redirect:/categories";
        }
    }
    
    // 카테고리 삭제
    @GetMapping("/{categoryId}/delete")
    public String deleteCategory(@PathVariable int categoryId) {
        Category existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory != null) {
            categoryService.deleteCategory(categoryId);
        }
        return "redirect:/categories";
    }
}

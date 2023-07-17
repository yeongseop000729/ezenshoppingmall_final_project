package com.javalab.board.service;

import java.util.List;

import com.javalab.board.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int categoryId);
    Category saveCategory(Category category);
    void deleteCategory(int categoryId);
	Category updateCategory(int categoryId, Category updatedCategory);
    
}
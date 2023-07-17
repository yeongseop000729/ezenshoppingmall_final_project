package com.javalab.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.board.entity.Category;
import com.javalab.board.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            // 필요한 다른 업데이트 로직 구현
            return saveCategory(existingCategory);
        }
        return null;
    }


    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
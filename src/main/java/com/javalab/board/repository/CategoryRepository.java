package com.javalab.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalab.board.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}

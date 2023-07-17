package com.javalab.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalab.board.entity.Basket;
import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer>{

	List<BasketProduct> findByBasketBasketNo(Integer basketNo);


    List<BasketProduct> findByBasket(Basket existingBasket);
}
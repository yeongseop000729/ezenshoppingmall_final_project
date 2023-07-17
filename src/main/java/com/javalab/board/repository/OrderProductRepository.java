package com.javalab.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalab.board.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>{

}

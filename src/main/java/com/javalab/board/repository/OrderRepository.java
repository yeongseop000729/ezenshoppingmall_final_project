package com.javalab.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalab.board.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}

package com.javalab.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalab.board.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer>{

	Optional<Basket> findBasketByMemberMemberId(Object member);

}
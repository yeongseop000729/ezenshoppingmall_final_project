package com.javalab.board.service;

import java.util.List;

import com.javalab.board.dto.BasketDTO;
import com.javalab.board.dto.BasketProductDTO;
import com.javalab.board.dto.UpdateBasketDTO;
import com.javalab.board.entity.Basket;

public interface BasketService {
	public  List<BasketProductDTO> getBasketProducts(Object member);
	public void updateBasket(UpdateBasketDTO updateBasketDTO);
	public BasketDTO getBasket(Object member);
	public void deleteBasketProduct(Integer basketProductNo);
	public Integer getCurrentTotalPrice(Integer basketNo);
	public void updateTotalPrice(Integer totalPrice, Integer basketNo);

	public void createBasket2(BasketDTO basketDTO, String memberId);

}
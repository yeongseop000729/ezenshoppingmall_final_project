package com.javalab.board.service;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.javalab.board.dto.BasketProductDTO;
import com.javalab.board.dto.OrderDTO;
import com.javalab.board.dto.OrderProductDTO;
import com.javalab.board.entity.Order;
import com.javalab.board.entity.OrderProduct;
import com.javalab.board.entity.Product;

public interface OrderService {

	public BasketProductDTO createOrderPage(int basketQty, int productNo);

	public List<BasketProductDTO> createOrdersPage(int basketProductNo);

	public void createOrder(OrderDTO orderDTO, OrderProductDTO orderProductDTO, String userId);

	public void createOrders(OrderDTO orderDTO, String userId);
	
	default OrderProduct dtoToEntity(OrderProductDTO orderProductDTO) {
		return OrderProduct.builder()
				.orderQty(orderProductDTO.getOrderQty())
				.orderProductAmount(orderProductDTO.getOrderProductAmount())
				/* .order(Order.builder().orderNo(orderProductDTO.getOrderNo()).build()) */
				.product(Product.builder().productNo(orderProductDTO.getProductNo()).build())
				.build();
	}

	

	
}
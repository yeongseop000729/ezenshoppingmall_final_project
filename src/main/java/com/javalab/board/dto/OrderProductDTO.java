package com.javalab.board.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Order;
import com.javalab.board.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderProductNo;
	
    private Order order;
	
    private String orderQty;

    private String orderProductAmount;
    
    private Product product;
    
    private Integer productNo;
    
    private int orderNo;

}
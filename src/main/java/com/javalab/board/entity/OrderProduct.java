package com.javalab.board.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
@ToString(exclude = { "product"})
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderProductNo;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;
	
    @Column(name = "order_qty")
    private String orderQty;

    @Column(name = "order_product_amount")
    private String orderProductAmount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;
    
    

}

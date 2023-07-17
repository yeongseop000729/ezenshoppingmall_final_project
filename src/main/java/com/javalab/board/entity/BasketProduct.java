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
@Table(name = "basket_product")
@ToString(exclude = {"member", "product", "basket"}) 
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer basketProductNo;


	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_no")
    private Basket basket;
	

 
    
//    @OneToOne
//    @JoinColumn(name = "basket_no")
//    private Basket basket;

    @Column(name = "basket_qty")
    private Integer basketQty;

    @Column(name = "basket_product_amount")
    private Integer basketProductAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;
}
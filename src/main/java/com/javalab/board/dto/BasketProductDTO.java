package com.javalab.board.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.javalab.board.entity.Basket;
import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductDTO {

    private Integer basketProductNo;

    private Basket basket;
 
    private Integer basketQty;

    private Integer basketProductAmount;

    private Product product;

    private ProductDTO productDTO;
}
package com.javalab.board.dto;

import java.util.List;

import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Category;
import com.javalab.board.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int productNo;
    private String productName;
    private String productDescription;
    private int productPrice;
    private byte[] productImage1;
    private byte[] productImage2;
    private byte[] productImage3;
    private Category category;
    private List<BasketProduct> basketProducts;
    private List<OrderProduct> orderProducts;
}
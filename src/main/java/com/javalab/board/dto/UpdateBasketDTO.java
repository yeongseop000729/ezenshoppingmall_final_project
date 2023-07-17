package com.javalab.board.dto;

import java.util.List;

import com.javalab.board.entity.Basket;
import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBasketDTO {
   
   private Integer basketProductNo;
   
    private Integer basketNo;
 
    private Integer basketQty;

    private Integer basketProductAmount;

    private Integer productNo;
    
    private Integer basketAmount;
    
    

}
package com.javalab.board.dto;

import java.util.List;
import java.util.ArrayList;

import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {
   
   private Integer basketNo;
   
   private Integer basketAmount;
    
   private Member member;
   
   private List<BasketProductDTO> basketProductDTOs;

   private List<BasketProduct> basketProducts;

}
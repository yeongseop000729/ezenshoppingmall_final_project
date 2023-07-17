package com.javalab.board.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.OrderProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	
    private int orderNo;

    private String orderReceiverName;

    private String orderReceiverPhone;

    private String orderReceiverAddress;

    private LocalDate orderDate = LocalDate.now();

    private Integer orderPaymentAmount;

    private Member member;
    
	/* private List<OrderProduct> orderProducts; */
    
    private List<OrderProductDTO> orderProducts;

}
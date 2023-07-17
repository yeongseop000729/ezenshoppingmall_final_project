package com.javalab.board.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "order_table")
@ToString(exclude = {"orderProducts", "member"})
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private int orderNo;

    private String orderReceiverName;


    private String orderReceiverPhone;


    private String orderReceiverAddress;

    @Column(name = "order_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Builder.Default
    private LocalDate orderDate = LocalDate.now();
    
	/*
	 * public LocalDate getOrderDate() { return orderDate; }
	 * 
	 * public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
	 */
    

    @Column(name = "order_paymentAmount")
    private Integer orderPaymentAmount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();


}

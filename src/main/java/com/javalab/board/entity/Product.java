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
import javax.persistence.Lob;
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
@Table(name = "product")
@ToString(exclude = {"category", "basketProducts", "orderProducts"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private int productNo;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price")
    private int productPrice;

    @Lob
    @Column(name = "product_image1", columnDefinition = "LONGBLOB")
    private byte[] productImage1;
    
    @Lob
    @Column(name = "product_image2", columnDefinition = "LONGBLOB")
    private byte[] productImage2;
    
    @Lob
    @Column(name = "product_image3", columnDefinition = "LONGBLOB")
    private byte[] productImage3;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<BasketProduct> basketProducts = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
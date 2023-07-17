package com.javalab.board.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "member_password", nullable = false)
    private String memberPassword;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_phone", nullable = false)
    private String memberPhone;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Column(name = "member_address", nullable = false)
    private String memberAddress;
    
    @Column(name = "admin", columnDefinition = "int default 0")
    private int admin;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Basket> baskets = new ArrayList<>();
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Order> orders = new ArrayList<>(); 
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Board> boards = new ArrayList<>();
}
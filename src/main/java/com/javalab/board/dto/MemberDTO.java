package com.javalab.board.dto;

import java.util.List;

import javax.persistence.Column;

import com.javalab.board.entity.Basket;
import com.javalab.board.entity.Board;
import com.javalab.board.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String memberAddress;
    private int admin;
    private List<Basket> baskets;
    private List<Order> orders;
    private List<Board> boards;
}
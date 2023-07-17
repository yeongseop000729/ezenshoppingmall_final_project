package com.javalab.board.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;



@Data
@Entity(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int replyId;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 120)
    String content;

    @ManyToOne
    @JoinColumn(name="boardNo")
    private Board board;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member member;

    @CreationTimestamp
    private Timestamp createDate;
    


};
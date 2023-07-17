package com.javalab.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private int reviewNo;

    @NotBlank
    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @NotBlank
    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @NotNull
    @Column(name = "review_updated", nullable = false)
    private LocalDate reviewUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}

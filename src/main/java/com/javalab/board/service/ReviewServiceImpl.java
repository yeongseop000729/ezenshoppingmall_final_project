package com.javalab.board.service;



import com.javalab.board.entity.Review;
import com.javalab.board.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    //전체 게시글 (관리자)
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    //전체 게시글(유저)
    @Override
    public List<Review> getReviewsByMemberId(String loggedInUser) {
        return reviewRepository.findByMemberMemberId(loggedInUser);
        
    }
    //글쓰기
    @Override
    public Review createReview(Review review , String loggedInUser) {
        if(loggedInUser != null) {
        	review.setReviewUpdated(LocalDate.now());
    	}
        return reviewRepository.save(review);
    }
    //리뷰 상세보기 
    @Override
    public Review getReviewById(int reviewNo) {
        return reviewRepository.findById(reviewNo)
        		.orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + reviewNo));
    }

    //글 수정
    public Review updateReview(int reviewNo, Review updatedReview) {
        Review review = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review No:" + reviewNo));

        review.setReviewTitle(updatedReview.getReviewTitle());
        review.setReviewContent(updatedReview.getReviewContent());
        review.setReviewUpdated(LocalDate.now());

        return reviewRepository.save(review);
    }
    //글지우기
    @Override
    public void deleteReview(int reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

   



}

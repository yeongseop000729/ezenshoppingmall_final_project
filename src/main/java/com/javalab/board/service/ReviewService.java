package com.javalab.board.service;


import com.javalab.board.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    Review updateReview(int reviewNo, Review Review);
    void deleteReview(int reviewNo);
    List<Review> getReviewsByMemberId(String loggedInUser);
    Review getReviewById(int reviewNo);
	Review createReview(Review review, String loggedInUser);
}

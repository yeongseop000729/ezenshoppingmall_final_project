package com.javalab.board.repository;


import com.javalab.board.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	//게시글 조회
	Review findByReviewNo(int ReviewNo);
	
    List<Review> findByMemberMemberId(String loggedInUser);
    
    //게시글 삭제 
	void delete(Review review);

	Optional<Review> findById(int reviewNo);
	

}
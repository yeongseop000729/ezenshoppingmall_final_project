package com.javalab.board.controller;

import com.javalab.board.entity.Board;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Review;
import com.javalab.board.service.ReviewService;
import com.javalab.board.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
    private MemberService memberService;
    
	@Autowired
    public ReviewController(ReviewService reviewService, MemberService memberService) {
        this.reviewService = reviewService;
        this.memberService = memberService;
    }
	//후기 게시판 전체보기
	@GetMapping
    public String getAllReviews(HttpSession session , Model model) {
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		
	          List<Review> reviews = reviewService.getAllReviews();
	          model.addAttribute("reviews", reviews);
	      return "review/review";
	  }
    //게시판 상세보기 
    @GetMapping("/reviewView")
    public String getBoardById(@RequestParam("reviewNo") int reviewNo, Model model) {
        Review review = reviewService.getReviewById(reviewNo);
        model.addAttribute("review", review);
        return "review/reviewView";
    }
   


    //글쓰기 페이지 구현 
	@GetMapping("/create")
	public String showCreateForm(Model model) {
	    model.addAttribute("review", new Review());
	    return "review/reviewForm";
	}
	//글쓰기 페이지 구현
	@PostMapping
	public String createReview(@ModelAttribute Review review, HttpSession session) {
	    String loggedInUser = (String) session.getAttribute("loggedInUser");
	    Member member = memberService.getMemberById(loggedInUser); // 현재 로그인한 사용자의 멤버 엔티티 가져오기
	    
	    if (loggedInUser != null){
	    	 review.setMember(member); // 멤버 엔티티를 board의 member 필드에 설정        
	         reviewService.createReview(review, loggedInUser);
	    }
	    return "redirect:/review";
	}

	 //글 지우기 구현 
    @GetMapping("/delete/{reviewNo}")
    public String deleteBoard(@PathVariable("reviewNo") int reviewNo) {
        reviewService.deleteReview(reviewNo);
        return "redirect:/review";
    }
    
      //글 수정 구현 
	  @GetMapping("/edit/{reviewNo}")
	  public String showUpdateForm(@PathVariable int reviewNo, Model model) {
	      Review review = reviewService.getReviewById(reviewNo);
	      System.out.println("Get");
	      model.addAttribute("review", review);
	      return "review/reviewEdit";
	  }
	  //글 수정 구현
	  @PostMapping("/edit/{reviewNo}")
	  public String updateReview(@PathVariable int reviewNo, @ModelAttribute Review updatedReview) {
		  System.out.println("Post");
	      reviewService.updateReview(reviewNo, updatedReview);
	      return "redirect:/review";
	  }
}

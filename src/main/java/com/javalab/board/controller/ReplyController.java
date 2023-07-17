package com.javalab.board.controller;

import javax.servlet.http.HttpSession;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.javalab.board.entity.Member;
import com.javalab.board.entity.Reply;
import com.javalab.board.service.ReplyService;
import com.javalab.board.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ReplyController {
	
    @Autowired
    private  ReplyService replyService;
    private final MemberService memberService;
   
    
   
    
    //댓글 작성
    @PostMapping("/reply_write")
    public String replyWrite(
    		@ModelAttribute Reply reply,
    		@RequestParam("boardNo") int boardNo, 
    		HttpSession session
    		) {
       
    	System.out.println("get---------------------------------");
    	
    	String loggedInUserId = (String) session.getAttribute("loggedInUser");
        if (loggedInUserId != null) {
            Member member = memberService.getMemberById(loggedInUserId);
            String redirectUrl = replyService.replyWrite(reply, member, boardNo);
            return "redirect:/board/boardView?boardNo=" + boardNo;
        } else {
            return "redirect:/login";
        }
    	
    }
    @PostMapping("/reply_delete")
    public String replyDelete(@RequestParam("replyId") int replyId, @RequestParam("boardNo") int boardNo , HttpSession session) {
    	String loggedInUserId = (String) session.getAttribute("loggedInUser");
        if (loggedInUserId != null) {
            Member member = memberService.getMemberById(loggedInUserId);
            replyService.replyDelete(replyId);
            return "redirect:/board/boardView?boardNo=" + boardNo;
        } else {
            // Handle case when user is not logged in
            return "redirect:/login";
        }
    }




};
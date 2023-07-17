package com.javalab.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javalab.board.dto.MemberDTO;
import com.javalab.board.entity.Member;
import com.javalab.board.service.MemberService;
import com.javalab.board.service.MemberServiceImpl;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    
    // 회원가입창 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
        return "account/register";
    }
    
    
    @PostMapping("/register")
    public String registerMember(@ModelAttribute("member") Member member, @RequestParam(required = false) String passwordConfirm, Model model) {
       // 비밀번호와 비밀번호 확인 필드의 값이 일치하는지 확인
       if (!member.getMemberPassword().equals(passwordConfirm)) {
          model.addAttribute("passwordMismatch", true); // 에러 메시지를 전달할 모델 속성 추가
       }
       memberService.register(member);
       return "redirect:/";
    }
    
    //로그인 페이지로 이동
    @GetMapping("/login")
    public String showLoginForm() {
        return "account/login";
    }
    
    
    // 로그인
    @PostMapping("/login")
    public String loginMember(@RequestParam("memberId") String memberId, @RequestParam("memberPassword") String memberPassword, HttpSession session, Model model) {
        if (memberService.authenticate(memberId, memberPassword)) {
            Member loggedInMember = memberService.getMemberById(memberId);
            session.setAttribute("loggedInUser", memberId); // Save the logged-in user's ID in the session
            if (loggedInMember.getAdmin() == 1) {
                session.setAttribute("isAdmin", true); // Save the admin status in the session
            } else {
                session.setAttribute("isAdmin", false); // Set isAdmin to false if admin value is not 1
            }
            return "main/main";
        } else {
            return "redirect:/login?error";
        }
    }
    

    
    
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }

    
    // 마이 페이지로 이동
    @GetMapping("/mypage/{memberId}")
    public String showMyPage(HttpSession session, Model model) {
        String loggedInUserId = (String) session.getAttribute("loggedInUser");
        if (loggedInUserId != null) {
            Member member = memberService.getMemberById(loggedInUserId);
            if (member != null) {
                model.addAttribute("member", member);
                return "account/mypage";
            }
        }
        return "account/login";
    }
    
    
    // 수정창으로 이동
    @GetMapping("/edit/{memberId}")
    public String showEditMemberForm(@PathVariable("memberId") String memberId, Model model) {
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "account/mypageEdit";
    }
   


	// 맴버 업데이트
    @PostMapping("/edit")
    public String editMember(@ModelAttribute("member") Member updatedMember) {
        memberService.updateMember(updatedMember);
        return "redirect:/mypage/" + updatedMember.getMemberId();
    }
    
    
    // DTO 변환 버전 안됨
	/*
	 * @PostMapping("/edit") public String updateMember(@ModelAttribute MemberDTO
	 * memberDTO) { Member existingMember =
	 * memberService.getMemberById(memberDTO.getMemberId()); if (existingMember ==
	 * null) { // Handle member not found error return "account/mypage"; } Member
	 * updatedMember = MemberServiceImpl.toEntity(memberDTO);
	 * updatedMember.setMemberId(existingMember.getMemberId());
	 * memberService.updateMember(updatedMember); return "account/mypage"; }
	 */
    
    
    // 멤버 삭제
    @PostMapping("/delete/{memberId}")
    public String deleteMember(@PathVariable String memberId, HttpSession session) {
        memberService.deleteMember(memberId);
        session.invalidate();
        return "redirect:/";
    }
    
    @PostMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@RequestBody String memberId) {
        boolean isDuplicate = memberService.isMemberIdExist(memberId);
        return ResponseEntity.ok(isDuplicate);
    }
  

}
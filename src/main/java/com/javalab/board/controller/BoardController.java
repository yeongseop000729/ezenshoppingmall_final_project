package com.javalab.board.controller;

import com.javalab.board.entity.Board;

import com.javalab.board.entity.Member;
import com.javalab.board.entity.Reply;
import com.javalab.board.service.BoardService;
import com.javalab.board.service.MemberService;
import com.javalab.board.service.ReplyService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpSession;



@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
    private MemberService memberService;
	@Autowired
	private  ReplyService replyService;
    
	 @Autowired
	    public BoardController(BoardService boardService, MemberService memberService , ReplyService replyService) {
	        this.replyService = replyService;
			this.boardService = boardService;
	        this.memberService = memberService;
	   
	 }
	 
//	  @GetMapping("/board/view/{boardNo}")
//	    public String getBoardView(@PathVariable("boardNo") int boardNo, Model model) {
//	        Board board = boardService.getBoardById(boardNo);
//	        
//	        model.addAttribute("board", board);
//	        model.addAttribute("replies", replies);
//	        return "board/boardView";
//	    }
	//문의 게시판 모든글 구현 
//    @GetMapping
//    public String getAllBoards(Model model) {
//        List<Board> boards = boardService.getAllBoards();
//        model.addAttribute("boards", boards);
//        return "board/board";
//    }
    //게시판 상세보기 
    @GetMapping("/boardView")
    public String getBoardById(@RequestParam  int boardNo, Model model) {
        Board board = boardService.getBoardById(boardNo);
        List<Reply> replies = replyService.getRepliesByBoard(board);
        model.addAttribute("board", board);
        model.addAttribute("replies", replies);
        
        
        System.out.println("replies------------------- ");
        System.out.println("replies------------------- " + replies.size());
        return "board/boardView";
    }
    

  //게시글 상세보기(아직 구현못함)
 // @GetMapping("/boardView{boardNo}")
  @GetMapping
  public String getAllBoards(HttpSession session, Model model) {
      String loggedInUser = (String) session.getAttribute("loggedInUser");
      Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

      if (loggedInUser != null && isAdmin != null && isAdmin) {
          // Admin 로그인 상태 처리
          // 예시: 모든 게시물 가져오기
          List<Board> boards = boardService.getAllBoards();
          model.addAttribute("boards", boards);
      } else if (loggedInUser != null) {
          // 사용자 로그인 상태 처리
          // 예시: 해당 사용자의 게시물 가져오기
    	 
          List<Board> boards = boardService.getBoardsByMemberId(loggedInUser);
          model.addAttribute("boards", boards);
          
          
      }
//       else {
//          // 로그인하지 않은 상태 처리
//          // 예시: 모든 게시물 가져오기
//          List<Board> boards = boardService.getAllBoards();
//          model.addAttribute("boards", boards);
//      }
      return "board/board";
  }
    
//    //게시글 전체보기
//    @GetMapping
//    public String getAllBoards(HttpSession session, Model model) {
//        String loggedInUser = (String) session.getAttribute("loggedInUser");
//        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
//
//        if (isAdmin != null && isAdmin) {
//            // Admin 로그인 상태 처리
//            // 예시: 모든 게시물 가져오기
//            List<Board> boards = boardService.getAllBoards();
//            model.addAttribute("boards", boards);
//        } else if (loggedInUser != null) {
//            // 사용자 로그인 상태 처리
//            // 예시: 해당 사용자의 게시물 가져오기
//            List<Board> boards = boardService.getBoardsByMemberId(loggedInUser);
//            model.addAttribute("boards", boards);
//        }
//        return "board/board";
//    }
    
    	//글쓰기 페이지 구현 
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/boardForm";
    }
    //글쓰기 페이지 구현
    @PostMapping
    public String createBoard(@ModelAttribute Board board, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberService.getMemberById(loggedInUser); // 현재 로그인한 사용자의 멤버 엔티티 가져오기
        
        if (loggedInUser != null){
        	 board.setMember(member); // 멤버 엔티티를 board의 member 필드에 설정        
             boardService.createBoard(board, loggedInUser);
        }
        return "redirect:/board";
    }

    //글 지우기 구현 
    @GetMapping("/delete/{boardNo}")
    public String deleteBoard(@PathVariable("boardNo") int boardNo) {
        boardService.deleteBoard(boardNo);
        return "redirect:/board";
    }
    
      //글 수정 구현 
	  @GetMapping("/edit/{boardNo}")
	  public String showUpdateForm(@PathVariable int boardNo, Model model) {
	      Board board = boardService.getBoardById(boardNo);
	      System.out.println("Get");
	      model.addAttribute("board", board);
	      return "board/boardEdit";
	  }
	  //글 수정 구현
	  @PostMapping("/edit/{boardNo}")
	  public String updateBoard(@PathVariable int boardNo, @ModelAttribute Board updatedBoard) {
		  System.out.println("Post");
	      boardService.updateBoard(boardNo, updatedBoard);
	      return "redirect:/board";
	  }
	  
//	  //글 수정
//	  @GetMapping("/edit/{boardNo}")
//	  public String showUpdateForm(@PathVariable int boardNo, Model model, HttpSession session) {
//	      String loggedInUser = (String) session.getAttribute("loggedInUser");
//	      
//	      if (loggedInUser == null) {
//	          // 로그인되지 않은 사용자는 글 수정 폼에 접근할 수 없음
//	          return "redirect:/login"; // 로그인 페이지로 리다이렉트 또는 다른 처리 방식 선택
//	      }
//	      
//	      Board board = boardService.getBoardById(boardNo);
//	      model.addAttribute("board", board);
//	      return "board/boardEdit";
//	  }
//
//	  @PostMapping("/edit/{boardNo}")
//	  public String updateBoard(@PathVariable int boardNo, @ModelAttribute Board updatedBoard, HttpSession session) {
//	      String loggedInUser = (String) session.getAttribute("loggedInUser");
//	      
//	      if (loggedInUser == null) {
//	          // 로그인되지 않은 사용자는 글 수정할 수 없음
//	          return "redirect:/login"; // 로그인 페이지로 리다이렉트 또는 다른 처리 방식 선택
//	      }
//	      
//	      boardService.updateBoard(boardNo, updatedBoard);
//	      return "redirect:/board";
//	  }
//    @GetMapping("/edit/{boardNo}")
//    public String showUpdateForm(@PathVariable int boardNo, Model model, HttpSession session) {
//        String loggedInUser = (String) session.getAttribute("loggedInUser");
//
//        Board board = boardService.getBoardById(boardNo);
//        if (!board.getMember().getMemberId().equals(loggedInUser)) {
//            throw new AccessDeniedException("You are not allowed to edit this post.");
//        }
//
//        model.addAttribute("board", board);
//        return "board/boardEdit";
//    }
//
//    @PostMapping("/edit/{boardNo}")
//    public String updateBoard(@PathVariable int boardNo, @ModelAttribute Board updatedBoard, HttpSession session) {
//        String loggedInUser = (String) session.getAttribute("loggedInUser");
//
//        Board board = boardService.getBoardById(boardNo);
//        if (!board.getMember().getMemberId().equals(loggedInUser)) {
//            throw new AccessDeniedException("You are not allowed to edit this post.");
//        }
//
//        boardService.updateBoard(boardNo, updatedBoard);
//        return "redirect:/board";
//    }
}
	  


//    // 문의게시판 상세보기 아직 구현못함 
//    @GetMapping("/boardView")
//    public String viewBoard(@PathVariable int boardNo, Model model) {
//        Board board = boardService.getBoardById(boardNo);
//        model.addAttribute("board", board);
//        model.addAttribute("boards", boardService.getAllBoards()); // 이 부분을 추가해주세요
//        return "board/boardView";
//    }
//    @GetMapping("/boardView/{boardNo}")
//    public String viewBoard(@PathVariable int boardNo, Model model) {
//        Board board = boardService.getBoardById(boardNo);
//        model.addAttribute("board", board);
//        model.addAttribute("boards", boardService.getAllBoards());
//        return "board/boardView";
//    }
//    public String getBoardById(@PathVariable int boardNo, Model model) {
//        Board board = boardService.getBoardById(boardNo);
//        model.addAttribute("board", board);
//        return "board/boardView";
//    }


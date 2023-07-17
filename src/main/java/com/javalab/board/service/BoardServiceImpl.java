package com.javalab.board.service;

import com.javalab.board.entity.Board;

import com.javalab.board.repository.BoardRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    //전체 게시글(관리자)
    @Override
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    
    //전체 게시글(유저)
    @Override
    public List<Board> getBoardsByMemberId(String loggedInUser) {
        return boardRepository.findByMemberMemberId(loggedInUser);
        
    }


//    //글 상세보기 화면
//    @Override
//    public Board getBoardById(int boardNo) {
//        return boardRepository.findById(boardNo)
//                .orElse(null);
//    }
//    //글 쓰기 구현 
//    @Override
//    public Board createBoard(Board board) {
//        board.setBoardUpdated(LocalDate.now());
//        return boardRepository.save(board);
//    }
    //글쓰기
    @Override
    public Board createBoard(Board board, String loggedInUser) {
        if (loggedInUser != null) {
            board.setBoardUpdated(LocalDate.now());  
        }
        return boardRepository.save(board);
    }


//    // 글 수정
//    @Override
//    public void updateBoard(int boardNo, Board updatedBoard) {
//        Board board = getBoardById(boardNo);
//        
//        board.setBoardTitle(updatedBoard.getBoardTitle());
//        board.setBoardContent(updatedBoard.getBoardContent());
//        board.setBoardUpdated(LocalDate.now());
//        boardRepository.save(board);
//    }
    //상세보기
    public Board getBoardById(int boardNo) {
        return boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board No:" + boardNo));
    }
    //글 수정
    public Board updateBoard(int boardNo, Board updatedBoard) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board No:" + boardNo));

        board.setBoardTitle(updatedBoard.getBoardTitle());
        board.setBoardContent(updatedBoard.getBoardContent());
        board.setBoardUpdated(LocalDate.now());

        return boardRepository.save(board);
    }

    
	//글 지우기 구현 
    @Override
    public void deleteBoard(int boardNo) {
        boardRepository.deleteById(boardNo);
    }
}


    
//    @Override
//    public List<Board> getBoardsByMemberId(String loggedInUserId) {
//        // 사용자 ID를 기반으로 해당 사용자의 게시물을 가져오는 로직을 구현해야 합니다.
//        // 예를 들어, 게시물 데이터베이스에서 해당 사용자의 게시물을 쿼리하여 반환하는 방식으로 구현할 수 있습니다.
//        // 아래는 가상의 메서드 호출을 사용하여 예시를 보여드립니다.
//        
//        List<Board> boards = boardRepository.findBoardsByMemberId(loggedInUserId);
//        
//        return boards;
//    }
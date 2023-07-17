package com.javalab.board.service;

import com.javalab.board.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> getAllBoards();
    //Board createBoard(Board board);
    Board updateBoard(int boardNo, Board board);
    void deleteBoard(int boardNo);
	List<Board> getBoardsByMemberId(String loggedInUser);
	Board getBoardById(int boardNo);
	Board createBoard(Board board, String loggedInUser);
}
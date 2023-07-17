package com.javalab.board.repository;

import com.javalab.board.entity.Board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	//게시글 조회
	Board findByBoardNo(int boardNo);
	
	
    // 게시글 삭제
    void delete(Board board);

   //List<Board> fiMendByMembermberId(String memberId);


   List<Board> findByMemberMemberId(String loggedInUser);


   	Optional<Board> findById(int boardNo);






}

package com.javalab.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalab.board.entity.Board;
import com.javalab.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {


	List<Reply> findByBoard(Board board);
	
	//List<Reply> findByBoard(Board board);
	
};
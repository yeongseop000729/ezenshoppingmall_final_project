package com.javalab.board.service;

import java.util.List;

import com.javalab.board.entity.Board;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Reply;

public interface ReplyService {
    String replyWrite(Reply reply, Member member, int boardNo);
    String replyDelete(int replyId);
    List<Reply> getRepliesByBoard(Board board);
}
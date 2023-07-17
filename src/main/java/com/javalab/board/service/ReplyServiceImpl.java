package com.javalab.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javalab.board.entity.Board;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Reply;
import com.javalab.board.repository.BoardRepository;
import com.javalab.board.repository.MemberRepository;
import com.javalab.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository userRepository;

    @Override
    public String replyWrite(Reply reply, Member member, int boardNo) {
        Member findMember = userRepository.findByMemberId(member.getMemberId());
        Optional<Board> findBoard = boardRepository.findById(boardNo);

        reply.setBoard(findBoard.get());
        reply.setMember(findMember);
        replyRepository.save(reply);

        return "/board/boardView";
    }

    @Override
    public String replyDelete(int replyId) {
        replyRepository.deleteById(replyId);
		return "board/boardView";
    }


    @Override
    public List<Reply> getRepliesByBoard(Board board) {
        return replyRepository.findByBoard(board);
    }


}
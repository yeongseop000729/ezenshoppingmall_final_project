package com.javalab.board.service;

import com.javalab.board.entity.Member;

public interface MemberService {
    void register(Member member);
    boolean authenticate(String memberId, String memberPassword);
    // 다른 회원 관련 기능에 대한 메소드 선언
	
    Member getMemberById(String memberId);
    void updateMember(Member member);
    void deleteMember(String memberId);
	boolean isMemberIdExist(String memberId);
	boolean isPasswordDuplicate(String memberPassword);

}

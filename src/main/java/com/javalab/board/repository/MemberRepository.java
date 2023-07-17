package com.javalab.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalab.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Member findByMemberId(String memberId);

}

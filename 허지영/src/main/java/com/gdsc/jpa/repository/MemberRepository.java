package com.gdsc.jpa.repository;


import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// DAO
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Team을 매개변수로 받아서 관련된 모든 멤버를 불러온다.

    List<Member> findAllByTeam(Team team);

    Page<Member> findAllByTeam(Team team, Pageable pageable); // 팀 기준으로 페이지

}
package com.gdsc.jpa.service;

import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.MemberRepository;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        Team team = teamRepository.findById(teamId)// teamId 로 team 찾기
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));


        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }
    @Transactional(readOnly = true)
    public Page<MemberDTO> findAllWithPaging(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        return  members.map(Member::toDTO);
    }
    @Transactional(readOnly = true)
    public Page<MemberDTO> findAllByTeamIdWithPaging(Long teamId, Pageable pageable) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        Page<Member> members = memberRepository.findAllByTeam(team, pageable);
        return members.map(Member::toDTO);
    }

    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {//id로 member찾기

        return findEntityById(id).toDTO();//id로 찾아 그 리턴값을 DTO로 바꿔서 반환
    }

    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
    }

    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}
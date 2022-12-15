package gdsc.skhu.jpa.service;

import gdsc.skhu.jpa.domain.Member;
import gdsc.skhu.jpa.domain.Team;
import gdsc.skhu.jpa.dto.MemberDTO;
import gdsc.skhu.jpa.repository.MemberRepository;
import gdsc.skhu.jpa.repository.TeamRepository;
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
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }

//    @Transactional(readOnly = true)
//    public List<MemberDTO> findAll() {
//        List<Member> members = memberRepository.findAll();
//
//        return members.stream()
//                .map(Member::toDTO)
//                .collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MemberDTO> findAllWithPaging(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        return members.map(Member::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<MemberDTO> findAllByTeamIdWithPaging(Long teamId, Pageable pageable) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음."));

        Page<Member> members = memberRepository.findAllByTeam(team, pageable);
        return members.map(Member::toDTO);
    }


    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDTO();
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

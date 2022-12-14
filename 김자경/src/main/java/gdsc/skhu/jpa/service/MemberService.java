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

@Service //서비스 등록
@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        Team team = teamRepository.findById(teamId)// teamId 로 team 찾기
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //없으면 Exception(예외)

        //팀에 입력받은 dto 기반으로 create member
        Member member = Member.builder()
                .name(dto.getName())//getName()으로 이름 꺼내기
                .age(dto.getAge())//getAge()로 나이 꺼내기
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
        Team team = teamRepository.findById(teamId)  // teamId 로 team 인스턴스 획득
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //없으면 Exception
        Page<Member> members=memberRepository.findAllByTeam(team,pageable);
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
        //id로 멤버를 찾고 dto로 바꿔서 update 후 리턴
    }

    @Transactional
    public void deleteById(Long id) { //member id와 매개변수 id가 일치하면 삭제
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    Member findEntityById(Long id) {//Id을 기반으로 실제 DB에 있는 영속성 엔티티를 찾아옴
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}
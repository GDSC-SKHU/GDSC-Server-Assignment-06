package gdsc.skhu.jpa.service;

import gdsc.skhu.jpa.domain.Team;
import gdsc.skhu.jpa.dto.TeamDTO;
import gdsc.skhu.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    // 의존성 주입
    // https://jgrammer.tistory.com/entry/springboot-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85-%EB%B0%A9%EC%8B%9D-%EA%B2%B0%EC%A0%95
    private final TeamRepository teamRepository;

    @Transactional
    public TeamDTO save(TeamDTO dto) {
        Team team = Team.builder()
                .name(dto.getName())
                .build();

        return teamRepository.save(team).toDTO();
    }

    @Transactional(readOnly = true)
    public List<TeamDTO> findAll() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        Team team = findEntityById(id);

        return team.toDTO();
    }

    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto) {
        Team team = findEntityById(id);
        team.update(dto);

        // https://velog.io/@codren/JPA-save-%EC%99%80-saveAndFlush-%EC%9D%98-%EC%B0%A8%EC%9D%B4
        return teamRepository.saveAndFlush(team).toDTO();
    }

    @Transactional
    public void deleteById(Long id) {
        Team team = findEntityById(id);

        teamRepository.delete(team);

        // 아래 방식도 가능하지만 위 방식으로 에러 핸들링을 지향하자
        // teamRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    Team findEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
    }
}

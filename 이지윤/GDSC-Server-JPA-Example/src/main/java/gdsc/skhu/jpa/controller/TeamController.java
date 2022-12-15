package gdsc.skhu.jpa.controller;

import gdsc.skhu.jpa.dto.TeamDTO;
import gdsc.skhu.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // https://mangkyu.tistory.com/49
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);

        // https://tecoble.techcourse.co.kr/post/2021-05-10-response-entity/
        return ResponseEntity
                .created(URI.create("/api/teams/" + response.getId()))
                .body(response);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable("id") Long id) {
        TeamDTO response = teamService.findById(id);

        return ResponseEntity.ok(response);
    }

    // PUT과 PATCH의 차이 https://programmer93.tistory.com/39
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable("id") Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}

package gdsc.skhu.jpa.controller;

import gdsc.skhu.jpa.dto.MemberDTO;
import gdsc.skhu.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }


    @GetMapping("/teams/{id}/members")
    public ResponseEntity<Page<MemberDTO>> findAllByTeamIdWithPaging(
            @PathVariable("id") Long id,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllByTeamIdWithPaging(id, pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }


//    @GetMapping("/teams/{id}/members")
//    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
//        List<MemberDTO> responses = memberService.findAllByTeamId(id);
//
//        if (responses.isEmpty()) {
//            return ResponseEntity
//                    .noContent()
//                    .build();
//        }
//
//        return ResponseEntity.ok(responses);
//    }

    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAllWithDefault(
            @PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/request")
    public ResponseEntity<Page<MemberDTO>> findAllWithRequest(
            @RequestParam("page") int page, @RequestParam("size") int size)  {

        Pageable pageable1 = PageRequest.of(page, size);
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable1);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAllWithPaging(
            Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);

    }
//    @GetMapping("/members")
//    public ResponseEntity<List<MemberDTO>> findAll() {
//        List<MemberDTO> responses = memberService.findAll();
//
//        if (responses.isEmpty()) {
//            return ResponseEntity
//                    .noContent()
//                    .build();
//        }
//
//        return ResponseEntity.ok(responses);
//    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}

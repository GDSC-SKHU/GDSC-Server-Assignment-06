package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 전달받은 id의 팀 멤버 저장
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        return ResponseEntity
                .created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }



    /*
    // 멤버 전체 조회
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> responses = memberService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    } */

    // import org.springframework.data.domain.Pageable
    // @RequestParam을 사용하는 API
    @GetMapping("/members/request")
    public ResponseEntity<Page<MemberDTO>> findAllWithPagingRequest (
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            Pageable pageable){
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    /*
    Pageable을 파라미터로 Controller를 구현하면 Request Parameter를 통하여 Handling 할 수 있는 파라미터는 기본적으로 page, size, sort
    page : 조회할 페이지 번호 (default : 0)
    size : 한 페이지 당 조회 개수 (default : 20)
    sort : 정렬 기준 (정렬할 기준 컬럼,ASC|DESC)
     */
    // Pageable만 사용하는 API
    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAllWithPaging(Pageable pageable){
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    // @PageableDefault를 사용하는 API
    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAllWithPagingDefault(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }


    /*
    // 전달받은 id의 팀 멤버 전체 조회
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    } */

    // pagination
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<Page<MemberDTO>> findAllByTeamIdWithPaging(
            @PathVariable("id") Long id,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<MemberDTO> responses = memberService.findAllByTeamIdWithPaging(id, pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }



    // 전달받은 id의 멤버 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    // 멤버 정보 갱신
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity
                .ok(response);
    }

    // 멤버 삭제
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }
}

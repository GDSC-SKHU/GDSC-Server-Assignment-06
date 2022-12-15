package com.gdsc.jpa.controller;

import com.gdsc.jpa.Service.MemberService;
import com.gdsc.jpa.dto.MemberDto;
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

@RestController//@ResponseBody + @Controller
@RequiredArgsConstructor//final 필드에 대해 생성자를 생성
@RequestMapping("/api")//매핑 어노테이션
public class MemberController {
    //MemberService 의존성 주입
    private final MemberService memberService;


   //@PageableDefault를 이용한 API
    @GetMapping("/members/PageableDefault")
    public ResponseEntity<Page<MemberDto>> findAll( @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MemberDto> responses = memberService.findAll(pageable);
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }
    //@RequestParam을 이용한 API
    @GetMapping("/members/RequestParam")
    public ResponseEntity<Page<MemberDto>> findAllWithRequest(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable) {
        //PageRequest 객체에 접근
        Pageable paging = PageRequest.of(page, size);
        Page<MemberDto> response = memberService.findAllWithPaging(paging);
        if (response.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(response);
    }
    //Pageable 사용한 API
    @GetMapping("/members/Pageable")
    public ResponseEntity<Page<MemberDto>> findAllWithPage(Pageable pageable) {
        Page<MemberDto> responses = memberService.findAllWithPaging(pageable);
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }


    //팀의 속한 멤버 모두 조회
    @GetMapping("/team/{id}/members")
    public ResponseEntity<Page<MemberDto>> findAllByTeamIdWithPaging(@PathVariable("id") Long id, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MemberDto> responses = memberService.findAllByTeamIdWithPaging(id, pageable);
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }
    /**
     * memberDto와 id를 파라미터로 받아 member를 저장한다.
     * @param id member의 id
     * @param request 저장할 memberDto
     * @return /api/members/{id} uri를 반환한다.
     */
    @PostMapping("/teams/{id}/member")
    public ResponseEntity<MemberDto> save(@PathVariable("id") Long id,@RequestBody MemberDto request) {
        MemberDto response = memberService.saveByTeamId(id,request);
        return ResponseEntity.created(URI.create("/api/members" + response.getId()))
                .body(response);
    }
    /**
     * Team에 속한 Member를 반환한다.
     * @param id TeamId를 파라미터로 받는다.
     * @return TeamID를 통해 조회한 members를 반환한다.
     * team에 속한 member가 없을경우 204 에러를 발생시킨다.
     */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDto>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDto> members = memberService.findAllByTeamId(id);
        if (members.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(members);
    }

    /**
     * id에 맞는 member 조회
     * @param id memberId를 파라미터로 받는다.
     * @return 조회한 member를 반환한다.
     */
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDto> findById(@PathVariable Long id) {
        MemberDto response = memberService.findById(id);
        return ResponseEntity
                .ok(response);
    }

    /**
     * member를 업데이트한다.
     * @param id memberID
     * @param request update할 memberDto
     * @return update한 MemberDto를 반환한다.
     */
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDto> updateById(@PathVariable("id") Long id, @RequestBody MemberDto request) {
        MemberDto response = memberService.updateById(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * id에 맞는 member 객체 삭제
     * @param id memberId를 파라미터로 받는다.
     * @return member를 삭제한후 null을 반환한다.
     */
    @DeleteMapping("members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}

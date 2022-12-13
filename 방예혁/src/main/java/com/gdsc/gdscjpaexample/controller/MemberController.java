package com.gdsc.gdscjpaexample.controller;

import com.gdsc.gdscjpaexample.dto.MemberDTO;
import com.gdsc.gdscjpaexample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/*
   [MemberController]
   @PageableDefault, get members (HTTP method: GET, API: /members/default)
   @RequestParam, get members    (HTTP method: GET, API: /members/request)
   Pageable, get members         (HTTP method: GET, API: /members/pageable)
   create member to team (HTTP method: POST, API: /teams/{id}/members)
   get members by team   (HTTP method: GET, API: /teams/{id}/members)
   get member by id      (HTTP method: GET, API: /members/{id})
   update member         (HTTP method: PATCH, API: /members/{id})
   delete member         (HTTP method: DELETE, API: /members/{id})
*/

@RestController
@RequiredArgsConstructor

// 지운거임 @RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    // @PageableDefault를 사용하는 API
    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAllWithDefault(
            // 5 사이즈로 나눠진 페이지 중 4 페이지, id를 기준으로 내림차순 정렬
            @PageableDefault(page = 4, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }

    // @RequestParam을 사용하는 API
    // 주소창으로 값을 넘겨줘야 함
    @GetMapping("/members/request")
    public ResponseEntity<Page<MemberDTO>> findAllWithRequest(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              Pageable pageable){
        Pageable paging = PageRequest.of(page, size);
        Page<MemberDTO> response = memberService.findAllWithPaging(paging);

        if(response.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    // Pageable만 사용하는 API
    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAllWithPaging(Pageable pageable){
        Page<MemberDTO> response = memberService.findAllWithPaging(pageable);
        // 비어있는 경우
        if(response.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        // 있으면 리턴
        return ResponseEntity.ok(response);
    }

    /* 먼저{id}로 팀을 정하고 그 팀에 name(이름)과 age(나이)를 이용해 멤버 생성
       /api/members/ 주소로 Post 요청이 들어오면 실행 */
    @PostMapping("/teams/{id}/member")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        // 생성한 멤버의 id를 이용해 /api/members/id 처럼 뒤에 붙여서 URI 만들어줌
        return ResponseEntity.created(URI.create("/api/member/" + response.getId()))
                .body(response);
    }

    // 팀의 전체 멤버
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<Page<MemberDTO>> findAllByTeamIdWithPaging(
            @PathVariable("id") Long id,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<MemberDTO> responses = memberService.findAllByTeamIdWithPaging(id, pageable);

        // 만약 DB에 지정한 팀의 멤버가 없다면
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        // 있다면 리턴
        return ResponseEntity.ok(responses);
    }

    /* id를 통해 멤버를 찾고, 그 멤버의 정보를 가져와서 리턴
       /members/(찾으려는 멤버의 id)로 get 요청이 들어오면 실행 */
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    /* id로 멤버를 찾은 다음에 그 멤버의 정보(여기선 name, age)를 업데이트함
       /teams/(업데이트하려는 멤버의 id)로 patch 요청이 들어오면 실행 */
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    /* id로 멤버를 찾아서 그 멤버를 (DB 에서) 제거
       /members/(삭제하려는 멤버의 id)로 delete 요청이 들어오면 실행 */
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }

}

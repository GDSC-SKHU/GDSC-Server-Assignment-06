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
//@RequestMapping("/api") // URL 을 컨트롤러의 메서드와 매핑할 때 사용하는 어노테이션
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id")Long id,@RequestBody MemberDTO request)
    {
        MemberDTO response = memberService.saveByTeamId(id,request);
        return ResponseEntity.created(URI.create("/api/members/"+response.getId()))
                .body(response);
    }

    @GetMapping("/teams/{id}/members")
    public ResponseEntity<Page<MemberDTO>> findAllByTeamWithPaging(@PathVariable("id") Long id, Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllByTeamIdWithPaging(id, pageable);
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }
    //@RequestParam을 사용하는 API
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

    //@PageableDefault를 사용하는 API
    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAllWithDefault(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }
    //Pageable만 사용하는 API
    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAllWithPaging(Pageable pageable){
        Page<MemberDTO> response = memberService.findAllWithPaging(pageable);
        if(response.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(response);
    }
    //    @GetMapping("/members")
//    public ResponseEntity<List<MemberDto>> findAll() {
//        List<MemberDto> response = memberService.findAll();
//
//        if (response.isEmpty()) {
//            return ResponseEntity
//                    .noContent()
//                    .build();
//        }
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id")Long id,@RequestBody MemberDTO request){

        MemberDTO response = memberService.updateById(id,request);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
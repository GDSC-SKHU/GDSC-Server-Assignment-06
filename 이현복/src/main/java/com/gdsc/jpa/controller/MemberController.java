package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;


    // PathVariable 어노테이션 : REST API에서 URI에 변수 값을 받아 올 수 있도록 하는 어노테이션
    // @PathVariable("id") Long teamId -> 메소드 정의에서 위에 쓴 변수명을 그대로 @PathVariable("변수명")으로 정의
    // @PathVariable Long id -> 변수가 하나라면 상관 x

    // RequestBody 어노테이션 : 클라이언트가 전송하는 Http 요청의 Body 내용을 Java Object로 변환시켜주는 역할.
    // => 쉽게 말해 POST 방식의 HTTP body안에 JSON 으로 넘어온 값을 VO(데이타 객체)에 바인딩
    // 출처 ) https://2ham-s.tistory.com/294
    @PostMapping("/teams/{id}/members")

    // ResponseEntity : 일반적인 API는 반환하는 리소스에 Value만 있지 않고 상태코드, 응답 메시지 등이 포함될 수 있도록 하는 클래스.
    public ResponseEntity<MemberDTO> save(@PathVariable("id") Long teamId, @RequestBody MemberDTO request) {

//        System.out.print("memberController Save : ");
//        System.out.println(request.getAge());

        MemberDTO response = memberService.saveByTeamId(teamId, request);

        // /api/members/id URI 생성
        return ResponseEntity.created(URI.create("/api/members/"+response.getId()))
                .body(response);

    }

//    @GetMapping("/teams/{id}/members")
//    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long teamId){
//        List<MemberDTO> responses = memberService.findAllByTeamId(teamId);
//
//        // 만약 팀에 들어간 사람이 없다면
//        if (responses.isEmpty()) {
//            // 빈칸 출력
//            return ResponseEntity
//                    .noContent()
//                    .build();
//        }
//
//        return ResponseEntity
//                .ok(responses);
//    }


    // PageableDefault 어노테이션 : 페이지가 된채로 적을라면 PageableDefault 어노테이션을 통해 페이지 불러오기 가능
    // @PageableDefault(page = 0, ...) -> 기초 정보를 직접 결정해 준 것 ( 말그대로 defalt )
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<Page<MemberDTO>> findAllByTeamIdWithPaging(
            @PathVariable("id") Long teamId,
            @PageableDefault(page = 0,size = 5,sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {

        Page<MemberDTO> responses = memberService.findAllByTeamIdWithPaging(teamId,pageable);


        // 만약 팀에 들어간 사람이 없다면
        if (responses.isEmpty()) {
            // 빈칸 출력
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAllDefault(
            @PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC)Pageable pageable
    ){
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }


    // PageAble 어노테이션 findAll
    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAllWithPaging(
            Pageable pageable
    ){
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/members/request")
    public ResponseEntity<Page<MemberDTO>> findAllRequest(
            Integer page, Integer size, String sort, String sortBy
    ){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("page : "+page+" "+size +" "+sort +" "+ sortBy);
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortBy);
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }



    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id){
        MemberDTO response = memberService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request){
        MemberDTO response = memberService.updateById(id,request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        memberService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }


}

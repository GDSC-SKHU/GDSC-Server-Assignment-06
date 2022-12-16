package gdsc.skhu.jpa.controller;

import gdsc.skhu.jpa.dto.MemberDTO;
import gdsc.skhu.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController2 {

    private final MemberService memberService;

    @GetMapping("/members/pageable")
    public ResponseEntity<Page<MemberDTO>> findAll1(Pageable pageable) {
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/default")
    public ResponseEntity<Page<MemberDTO>> findAll2
            (@PageableDefault(page = 0,size = 5,sort = "id", direction = Sort.Direction.ASC)Pageable pageable)
    {
        Page<MemberDTO> responses = memberService.findAllWithPaging(pageable);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/request")
    public ResponseEntity<Page<MemberDTO>> findAll3(
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
    
}

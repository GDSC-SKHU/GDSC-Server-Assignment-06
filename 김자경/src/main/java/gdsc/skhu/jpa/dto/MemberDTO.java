package gdsc.skhu.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

// DAO, DTO, VO의 개념 https://choitaetae.tistory.com/97
// DTO는 불변, 가변으로 정의할 수 있다. 상황에 맞게 쓰자.
// https://velog.io/@byeongju/%EB%B6%88%EB%B3%80-%EA%B0%9D%EC%B2%B4
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    private int age;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}

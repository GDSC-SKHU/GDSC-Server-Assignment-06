package gdsc.skhu.jpa.domain;

import gdsc.skhu.jpa.dto.TeamDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Getter를 지향하고 Setter를 지양하자.(OCP: 개방폐쇄의 원칙)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// Entity로 지정
@Entity
// 연결한 DB에서 team라는 테이블이랑 매핑
@Table(name = "team")
@Builder
public class Team extends BaseTime {
    // PK로 지정
    @Id
    // 기본 키 생성을 데이터베이스에 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /*
    이런식으로 양방향 설정도 가능
    상황에 맞게 사용하자.
    https://ict-nroo.tistory.com/122
     */
    // SET ON DELETE
    // https://tecoble.techcourse.co.kr/post/2021-08-15-jpa-cascadetype-remove-vs-orphanremoval-true/
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public TeamDTO toDTO() {
        // Builder를 이용해서 가독성이 좋게 객체를 생성할 수 있다.
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(TeamDTO dto) {
        this.name = dto.getName();
    }
}

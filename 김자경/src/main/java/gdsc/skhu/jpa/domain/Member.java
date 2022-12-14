package gdsc.skhu.jpa.domain;

import gdsc.skhu.jpa.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;

// Getter를 지향하고 Setter를 지양하자.(OCP: 개방폐쇄의 원칙)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// Entity로 지정
@Entity
// 연결한 DB에서 member라는 테이블이랑 매핑
@Table(name = "member")
@Builder
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    // 단방향 N:1 설정, 지연로딩(멤버에서 팀을 조회할 때 쿼리가 두번나감)
    // 그 밖에 즉시로딩은 멤버를 불러오는 동시에 팀을 불러오기때문에 쿼리가 적게나감 https://deveric.tistory.com/56
    // 상황에 맞게 지연로딩, 즉시로딩을 사용하고 되도록 지연로딩을 지향하자.
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDTO dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}

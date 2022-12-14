package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>{
//JpaRepository를 상속받도록 하여 기본적인 동작이 모두 가능해짐,
// 어떤 엔티티를 메서드의 대상으로 할지 키워드로 지정

    List<Member> findAllByTeam(Team team);
    Page<Member> findAllByTeam(Team team, Pageable pageable);
    //그 와애도 JPQL을 이용해서 직접 쿼리문을 만들 수 있다
    //팀 기준으로 분류해놓고 거기서 팀을 기준으로 페이징
}

/*
Repository
- Entity에 의해 생성된 데베에 접근하는 메서드들을 사용하기 위한 인터페이스이다
엔티티를 선언하여 데베 구조를 만들었다면 여기어 어떤 값을 넣거나, 넣어진 값을 조회하는 등의 CRUD
를 어떻게 할 것인지 정의해주는 계층
 */
/*
DAO ?
-Data Access Object
-데베를 사용해 데이터를 조회하거나 조직하는 기능을 전담하도록 만든 객체
 */

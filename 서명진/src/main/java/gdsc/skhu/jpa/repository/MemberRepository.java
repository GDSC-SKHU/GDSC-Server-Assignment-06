package gdsc.skhu.jpa.repository;

import gdsc.skhu.jpa.domain.Member;
import gdsc.skhu.jpa.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// DAO
public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
    Team을 매개변수로 받아서 관련된 모든 멤버를 불러온다.

    SQL
    select *
    from member
    where team_id=?
     */
    List<Member> findAllByTeam(Team team);

    Page<Member> findAllByTeam(Team team,Pageable pageable);

    // 그 외에도 JPQL을 이용해서 직접 쿼리문을 만들 수 있다.
    // https://wonit.tistory.com/470
}
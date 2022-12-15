package gdsc.skhu.jpa.repository;

import gdsc.skhu.jpa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
public interface TeamRepository extends JpaRepository<Team, Long> {
}
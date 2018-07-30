package dao;

import domain.Participations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationsDao extends JpaRepository<Participations, Long> {
    @Query(value = "SELECT * FROM participations WHERE user_id = ?1 AND project_id = ?2 LIMIT 1", nativeQuery = true)
    Participations findByUserIdAndProjectId(Long userId, Long projectId);
}

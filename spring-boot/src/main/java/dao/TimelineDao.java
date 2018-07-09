package dao;

import domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TimelineDao extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM timelines where project_id = :projectId", nativeQuery = true)
    List<Map<String, Object>> findByProjectId(@Param("projectId") Long projectId);
}

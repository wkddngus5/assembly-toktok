package dao;

import domain.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface TimelineDao extends JpaRepository<Timeline, Long> {
    @Query(value = "SELECT * FROM timelines where project_id = :projectId", nativeQuery = true)
    List<Map<String, Object>> findByProjectId(@Param("projectId") Long projectId);
}

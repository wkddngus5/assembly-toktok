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

    @Modifying
    @Transactional
    @Query(value = "UPDATE timelines SET body = ?2, image = ?3, congressmanId = ?4, timeline_date = ?5, updated_at = ?6 WHERE id = ?1", nativeQuery = true)
    void updateTimelinkeById(Long id, String body, String image, Long congressmanId, String timelineDate, String updateDate);
}

package dao;

import domain.ProjectJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectJoinDao extends JpaRepository<ProjectJoin, Long> {

    @Query(value = "SELECT * FROM participations WHERE project_id = ?1 AND user_id = ?2", nativeQuery = true)
    ProjectJoin selectByProjectIdAndUserId(Long project_id, Long user_id);
}

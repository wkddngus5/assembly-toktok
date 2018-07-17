package dao;

import domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectDao extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects order by participations_count desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selectByBest();

    @Query(value = "SELECT * FROM projects order by deleted_at desc LIMIT 0, 5", nativeQuery = true)
    List<Project> selectByImminent();

    @Query(value = "SELECT * FROM projects order by created_at desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selecteByCreateTime();

    @Query(value = "SELECT * FROM projects where :where ", nativeQuery = true)
    List<Project> sorted(@Param("where") String where, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE projects SET participations_count = participations_count - 1 WHERE id = ?1", nativeQuery = true)
    void removeParticipation(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE projects SET participations_count = participations_count + 1 WHERE id = ?1", nativeQuery = true)
    void addParticipation(Long id);
}

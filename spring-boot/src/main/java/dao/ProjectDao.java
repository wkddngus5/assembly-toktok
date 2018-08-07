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
    @Query(value = "SELECT * FROM projects WHERE deleted_at IS NULL", nativeQuery = true)
    List<Project> findAll();

    @Query(value = "SELECT * FROM projects WHERE deleted_at IS NULL order by participations_count desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selectByBest();

    @Query(value = "SELECT * FROM projects WHERE deleted_at IS NULL order by deleted_at desc LIMIT 0, 5", nativeQuery = true)
    List<Project> selectByImminent();

    @Query(value = "SELECT * FROM projects WHERE deleted_at IS NULL order by created_at desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selecteByCreateTime();

    @Query(value = "SELECT * FROM projects where :where AND deleted_at IS NULL", nativeQuery = true)
    List<Project> sorted(@Param("where") String where, Pageable pageable);

    @Query(value = "SELECT * FROM projects where title like :keyword  or body like :keyword ", nativeQuery = true)
    List<Project> search(@Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query(value = "UPDATE projects SET participations_count = participations_count - 1 WHERE id = ?1", nativeQuery = true)
    void removeParticipation(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE projects SET participations_count = participations_count + 1 WHERE id = ?1", nativeQuery = true)
    void addParticipation(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE projects SET deleted_at = ?2 WHERE id = ?1 AND deleted_at IS NULL", nativeQuery = true)
    void deleteProject(Long id, String deleteAt);
}

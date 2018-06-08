package dao;

import domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectDao  extends JpaRepository<Project, Long> {
    @Query(value="SELECT * FROM projects order by participations_count desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selectByBest();

    @Query(value = "SELECT * FROM projects order by deleted_at desc LIMIT 0, 5", nativeQuery = true)
    List<Project> selectByImminent();

    @Query(value = "SELECT * FROM projects order by created_at desc LIMIT 0, 3", nativeQuery = true)
    List<Project> selecteByCreateTime();
}

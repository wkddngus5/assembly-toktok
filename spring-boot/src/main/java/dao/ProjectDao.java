package dao;

import domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectDao  extends JpaRepository<Project, Long> {
    @Query(value="SELECT * FROM projects order by participations_count desc LIMIT 0, 12", nativeQuery = true)
    List<Project> selectHottest();
}

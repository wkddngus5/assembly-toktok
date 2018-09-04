package dao;

import domain.Comment;
import domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CommentDao extends JpaRepository<Comment, Long> {
//    @Query(value = "SELECT * FROM comments where commentable_id = :projectId", nativeQuery = true)
//    List<Comment> findByProjectId(@Param("projectId") Long projectId);

    @Query(value = "SELECT * FROM comments where commentable_id = :projectId", nativeQuery = true)
    List<Comment> findByProjectId(@Param("projectId") Long projectId, Pageable pageable);

    @Query(value = "SELECT COUNT(id) FROM comments WHERE commentable_id = :projectId", nativeQuery = true)
    int countByProjectId(@Param("projectId") Long projectId);
}

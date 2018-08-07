package dao;

import domain.Likes;
import domain.ProjectJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikesDao extends JpaRepository<Likes, Long> {
    @Query(value = "SELECT * FROM likes WHERE likable_id = ?1 AND user_id = ?2", nativeQuery = true)
    Likes findByLikable_idAndUser_id(Long likable_id, Long user_id);

    @Query(value = "SELECT * FROM likes WHERE user_id = ?1", nativeQuery = true)
    List<Likes> findByUser_id(Long user_id);
}

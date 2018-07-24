package dao;

import domain.Congressmen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CongressmenDao extends JpaRepository<Congressmen, Long> {
    @Query(value = "SELECT c2.id, c2.name, c2.description, c2.party, c2.committee_id, c2.created_at, c2.updated_at, c2.image, c2.facebook_url, c2.twitter_url, c2.email, c2.homepage_url FROM committees c1, congressmen c2 WHERE c1.id = c2.committee_id AND c1.name = ?1", nativeQuery = true)
    List<Congressmen> findByNameCongressmen(String name);
}
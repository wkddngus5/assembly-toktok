package dao;

import domain.Committee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommitteeDao extends JpaRepository<Committee, Long> {
    @Query(value = "SELECT * FROM committees WHERE name = ?1", nativeQuery = true)
    Committee findByName(String name);
}
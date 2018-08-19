package dao;

import domain.MainSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainSlideDao extends JpaRepository<MainSlide, Long> {
    @Query(value = "SELECT * FROM mainslides order by mainslides.order asc", nativeQuery = true)
    List<MainSlide> findAllOrOrderByOrderAsc();
}

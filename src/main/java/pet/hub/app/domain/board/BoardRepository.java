package pet.hub.app.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT p FROM Board p WHERE p.title LIKE %?1% OR p.content LIKE %?1%")
    List<Board> findByTitleContainingOrContentContaining(String keyword);

    @Query("SELECT p FROM Board p WHERE p.title LIKE %?1%")
    List<Board> findByTitleContaining(String keyword);

    @Query("SELECT p FROM Board p WHERE p.content LIKE %?1%")
    List<Board> findByContentContaining(String keyword);
}
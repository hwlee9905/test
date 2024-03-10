package pet.hub.app.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.hub.app.domain.user.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.* FROM user u JOIN authentication a ON u.id = a.id WHERE a.userId = :userId", nativeQuery = true)
    User findByUserId(@Param("userId") String userId);
}

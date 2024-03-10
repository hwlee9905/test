package pet.hub.app.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.hub.app.domain.user.entity.Authentication;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
}

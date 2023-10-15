package success.navigator.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import success.navigator.website.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

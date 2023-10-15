package success.navigator.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import success.navigator.website.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}

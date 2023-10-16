package success.navigator.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import success.navigator.website.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

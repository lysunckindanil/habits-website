package success.navigator.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import success.navigator.website.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

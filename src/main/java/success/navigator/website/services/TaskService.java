package success.navigator.website.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Task;
import success.navigator.website.repositories.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTaskList() {
        return taskRepository.findAll();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void edit(Task task) {
        Task original_task = getTaskById(task.getId());
        original_task.setName(task.getName());
        original_task.setDescription(task.getDescription());
        original_task.setImage(task.getImage());
        original_task.setPoints(task.getPoints());
        save(original_task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}

package success.navigator.website.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Category;
import success.navigator.website.entities.Task;
import success.navigator.website.repositories.CategoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskService taskService;

    public Category getCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) return categoryRepository.findById(id).get();
        else return new Category();
    }

    public List<Category> getCategoriesList() {
        return categoryRepository.findAll();
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }


    // related to tasks
    public List<Task> getFreeTaskList() {
        Set<Task> usedTasks = new HashSet<>(categoryRepository.findAll().stream().flatMap(x -> x.getTasks().stream()).toList());
        return taskService.getTaskList().stream().filter(x -> !usedTasks.contains(x)).toList();
    }

    public void deleteTaskInCategoryById(Long categoryId, Long taskId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category = categoryRepository.findById(categoryId).get();
            category.getTasks().removeIf(x -> x.getId() == taskId);
            categoryRepository.save(category);
        }
    }

    public void addTaskInCategoryById(Long categoryId, Long taskId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category = categoryRepository.findById(categoryId).get();
            Task task = taskService.getTaskById(taskId);
            if (task != null) {
                category.getTasks().add(task);
                categoryRepository.save(category);
            }
        }
    }


}

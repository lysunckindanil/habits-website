package success.navigator.website.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Category;
import success.navigator.website.entities.Task;
import success.navigator.website.repositories.CategoryRepository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskService taskService;

    public Category getCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) return categoryRepository.findById(id).get();
        else return new Category();
    }

    public List<Category> getCategoriesList() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(x -> x.getTasks().sort(Comparator.comparingInt(Task::getPoints)));
        return categories;
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
        } else {
            log.error("Category doesn't exist by id");
        }
    }

    public void addTaskInCategoryById(Long categoryId, Long taskId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category = categoryRepository.findById(categoryId).get();
            Task task = taskService.getTaskById(taskId);
            if (task != null) {
                category.getTasks().add(task);
                categoryRepository.save(category);
            } else {
                log.error("Task doesn't exist by id");
            }
        } else {
            log.error("Category doesn't exist by id");
        }
    }


}

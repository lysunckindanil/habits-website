package success.navigator.website.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Category;
import success.navigator.website.entities.Task;
import success.navigator.website.repositories.CategoryRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TaskService taskService;

    public Category getCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) return categoryRepository.findById(id).get();
        else throw new RuntimeException("Category not found");
    }

    public List<Category> getCategoriesList() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(x -> x.getTasks().sort(Comparator.comparingInt(Task::getPoints)));
        return categories;
    }

    public void edit(Category category) {
        Category original_category = getCategoryById(category.getId());
        original_category.setId(category.getId());
        original_category.setName(category.getName());
        original_category.setDescription(category.getDescription());
        original_category.setCategoryOrder(category.getCategoryOrder());
        save(original_category);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    // related to tasks
    public void saveWithTasks(Category category, long[] tasks_ids) {
        List<Task> tasks = new ArrayList<>();
        for (long task_id : tasks_ids) {
            tasks.add(taskService.getTaskById(task_id));
        }
        category.setTasks(tasks);
    }

    public List<Task> getFreeTaskList() {
        Set<Task> usedTasks = new HashSet<>(categoryRepository.findAll().stream().flatMap(x -> x.getTasks().stream()).toList());
        return taskService.getTaskList().stream().filter(x -> !usedTasks.contains(x)).toList();
    }

    public void deleteTaskFromCategoryById(Long categoryId, Long taskId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category = categoryRepository.findById(categoryId).get();
            category.getTasks().removeIf(x -> x.getId() == taskId);
            categoryRepository.save(category);
        } else {
            log.error("deleteTaskInCategoryById: category doesn't exist by id");
        }
    }

    public void addTaskToCategoryById(Long categoryId, Long taskId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category = categoryRepository.findById(categoryId).get();
            Task task = taskService.getTaskById(taskId);
            if (task != null) {
                category.getTasks().add(task);
                categoryRepository.save(category);
            } else {
                log.error("addTaskInCategoryById: task doesn't exist by id");
            }
        } else {
            log.error("addTaskInCategoryById: category doesn't exist by id");
        }
    }


}

package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import success.navigator.website.entities.Category;
import success.navigator.website.entities.Task;
import success.navigator.website.services.CategoryService;
import success.navigator.website.services.TaskService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final TaskService taskService;

    // everything secured (/categories)
    @GetMapping("/add")
    private String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("tasks", taskService.getTaskList());
        return "categories/new";
    }

    @PostMapping("/add")
    private String addCategoryPost(@ModelAttribute Category category, @RequestParam(value = "task", required = false) String[] tasks_ids) {
        if (tasks_ids != null) {
            List<Task> tasks = new ArrayList<>();
            for (String task_id : tasks_ids) {
                tasks.add(taskService.getTaskById(Long.parseLong(task_id)));
            }
            category.setTasks(tasks);
        }
        categoryService.add(category);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    private String editCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("tasks", categoryService.getFreeTaskList());
        return "categories/edit";
    }

    @PostMapping("/{id}/edit")
    private String editCategoryPost(@ModelAttribute Category category) {
        Category original_category = categoryService.getCategoryById(category.getId());
        original_category.setId(category.getId());
        original_category.setName(category.getName());
        original_category.setDescription(category.getDescription());
        original_category.setCategoryOrder(category.getCategoryOrder());
        categoryService.add(original_category);
        return "redirect:/categories/%d/edit".formatted(category.getId());
    }

    @PostMapping("/{id}/delete")
    private String deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{category}/{task}/delete")
    public String deleteTaskInCategoryById(@PathVariable("category") Long categoryId, @PathVariable("task") Long taskId) {
        categoryService.deleteTaskInCategoryById(categoryId, taskId);
        return "redirect:/categories/%d/edit".formatted(categoryId);
    }

    @PostMapping("/{id}/add")
    public String addTaskToCategory(@PathVariable Long id, @RequestParam(value = "task", required = false) String[] tasks_ids) {
        System.out.println(Arrays.toString(tasks_ids));
        System.out.println(id);
        if (tasks_ids != null) {
            for (String taskId : tasks_ids) {
                categoryService.addTaskInCategoryById(id, Long.parseLong(taskId));
            }
        }
        return "redirect:/categories/%d/edit".formatted(id);
    }
}

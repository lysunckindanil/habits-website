package success.navigator.website.controllers.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import success.navigator.website.entities.Category;
import success.navigator.website.services.CategoryService;
import success.navigator.website.services.TaskService;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final TaskService taskService;

    // everything secured (/categories/**)
    @GetMapping("/add")
    private String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("tasks", taskService.getTaskList());
        return "categories/new";
    }

    @PostMapping("/add")
    private String addCategoryPost(@ModelAttribute Category category, @RequestParam(value = "task", required = false) long[] tasks_ids) {
        if (tasks_ids != null) {
            categoryService.saveWithTasks(category, tasks_ids);
        }
        categoryService.save(category);
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
        categoryService.edit(category);
        return "redirect:/categories/%d/edit".formatted(category.getId());
    }

    @PostMapping("/{id}/delete")
    private String deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{category}/{task}/delete")
    public String deleteTaskToCategoryById(@PathVariable("category") long categoryId, @PathVariable("task") long taskId) {
        categoryService.deleteTaskFromCategoryById(categoryId, taskId);
        return "redirect:/categories/%d/edit".formatted(categoryId);
    }

    @PostMapping("/{id}/add")
    public String addTaskToCategory(@PathVariable Long id, @RequestParam(value = "task", required = false) long[] tasks_ids) {
        if (tasks_ids != null) {
            for (long taskId : tasks_ids) {
                categoryService.addTaskToCategoryById(id, taskId);
            }
        }
        return "redirect:/categories/%d/edit".formatted(id);
    }
}

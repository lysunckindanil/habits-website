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
import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final TaskService taskService;


    @GetMapping("/add")
    private String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("tasks", taskService.getTaskList());
        return "categories/new";
    }

    @PostMapping("/add")
    private String addCategoryPost(@ModelAttribute Category category, @RequestParam("task") String[] tasks_ids) {
        List<Task> tasks = new ArrayList<>();
        for (String task_id : tasks_ids) {
            tasks.add(taskService.getTaskById(Long.parseLong(task_id)));
        }
        category.setTasks(tasks);
        categoryService.add(category);
        return "redirect:/tasks";
    }
}

package success.navigator.website.controllers.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import success.navigator.website.entities.Task;
import success.navigator.website.services.TaskService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    // secured everything ("/tasks/**")
    @GetMapping("/add")
    private String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    @PostMapping("/add")
    private String addTaskPost(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/admin/tasks";
    }

    @GetMapping("/{id}/edit")
    private String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "tasks/edit";
    }

    @PostMapping("/{id}/edit")
    private String editTaskPost(@ModelAttribute Task task) {
        taskService.edit(task);
        return "redirect:/admin/tasks";
    }

    @PostMapping("/{id}/delete")
    private String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/admin/tasks";
    }
}


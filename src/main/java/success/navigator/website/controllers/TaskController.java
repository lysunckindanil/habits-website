package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import success.navigator.website.entities.Task;
import success.navigator.website.services.TaskService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}/edit")
    private String getTaskById(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            model.addAttribute("task", taskService.getTaskById(id));
            return "tasks/edit";
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    private String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }
}

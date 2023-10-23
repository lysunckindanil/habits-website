package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import success.navigator.website.entities.Task;
import success.navigator.website.entities.User;
import success.navigator.website.services.CategoryService;
import success.navigator.website.services.TaskService;
import success.navigator.website.services.UserService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final CategoryService categoryService;

    // secured (/tasks/add, /tasks/*/edit, /tasks/*/delete)
    @GetMapping()
    private String index(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("categories", categoryService.getCategoriesList().stream().sorted().toList());
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().stream().anyMatch(x -> x.getName().equals("ROLE_ADMIN")));
        return "tasks/index";
    }

    @GetMapping("/add")
    private String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    @PostMapping("/add")
    private String addTaskPost(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/admin/tasks";
    }

    @GetMapping("/{id}/edit")
    private String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            model.addAttribute("task", taskService.getTaskById(id));
            return "tasks/edit";
        }
        return "redirect:/";
    }

    @PostMapping("/{id}/edit")
    private String editTaskPost(@ModelAttribute Task task, @PathVariable Long id) {
        Task original_task = taskService.getTaskById(id);
        if (original_task != null) {
            original_task.setName(task.getName());
            original_task.setDescription(task.getDescription());
            original_task.setImage(task.getImage());
            original_task.setPoints(task.getPoints());
            taskService.add(original_task);
        }
        return "redirect:/admin/tasks";
    }

    @PostMapping("/{id}/delete")
    private String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/admin/tasks";
    }

    @PostMapping("/count")
    @ResponseBody
    private Map<String, String> addPointsToUser(@RequestBody Map<String, String> request, Principal principal) {
        if (request.containsKey("points")) {
            userService.addPointsToUser(principal.getName(), Integer.valueOf(request.get("points")));
            return request;
        }
        return null;
    }
}


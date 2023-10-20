package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import success.navigator.website.services.CategoryService;
import success.navigator.website.services.ImageService;
import success.navigator.website.services.TaskService;
import success.navigator.website.services.UserService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CategoryService categoryService;
    private final TaskService taskService;
    private final ImageService imageService;
    private final UserService userService;

    // everything secured (/admin)

    @GetMapping()
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getCategoriesList());
        return "admin/categories";
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskService.getTaskList());
        return "admin/tasks";
    }

    @GetMapping("/images")
    public String images(Model model) {
        model.addAttribute("images", imageService.getStaticImages());
        return "admin/images";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserFromDatabase(id);
        return "redirect:/admin/users";
    }
}

package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import success.navigator.website.entities.User;
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
    public String users(Model model, @ModelAttribute User user) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserFromDatabase(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/grant")
    public String grantAdmin(@RequestParam(name = "username") String username){
        userService.grantAdminRoleToUser(username);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/reduce")
    public String reduceAdmin(@RequestParam(name = "username") String username){
        userService.deleteAdminRoleToUser(username);
        return "redirect:/admin/users";
    }
}

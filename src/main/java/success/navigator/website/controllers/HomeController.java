package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import success.navigator.website.entities.User;
import success.navigator.website.services.CategoryService;
import success.navigator.website.services.UserService;

import java.security.Principal;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping()
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        model.addAttribute("principal", principal);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        model.addAttribute("principal", principal);
        return "about";
    }

    @GetMapping("/challenge")
    private String challenge(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("categories", categoryService.getCategoriesList().stream().sorted().toList());
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().stream().anyMatch(x -> x.getName().equals("ROLE_ADMIN")));
        return "challenge";
    }

}

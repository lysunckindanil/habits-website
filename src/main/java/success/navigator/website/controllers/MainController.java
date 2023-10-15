package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import success.navigator.website.entities.User;
import success.navigator.website.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping()
    public String index(Model model, Principal principal) {
        if (principal == null) return "index";
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "index-auth";
    }

    // registration
    @GetMapping("/register")
    public String registerForm(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new User());
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.addUserToDatabase(user);
        return "redirect:/";
    }

    // login
    @GetMapping("/login")
    public String loginForm(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        return "redirect:/";
    }

}

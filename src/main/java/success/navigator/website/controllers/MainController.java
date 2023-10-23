package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import success.navigator.website.entities.User;
import success.navigator.website.services.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping()
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", userService.findByUsername(principal.getName()));
        }
        model.addAttribute("principal", principal);
        return "index";
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

    // registration
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(@RequestBody Map<String, String> request) {
        int str_max = 20;
        int str_min = 5;
        String username = request.get("username");
        String password = request.get("password");
        Map<String, String> response = new HashMap<>();
        response.put("response", "failure");

        if (userService.findByUsername(request.get("username")) != null) {
            response.put("target", "username");
            response.put("message", "Sorry, %s already exists!".formatted(username));
        } else if (username.length() < str_min || username.length() > str_max) {
            response.put("target", "username");
            response.put("message", "Username should be between %d and %d characters!".formatted(str_min, str_max));
        } else if (password.length() < str_min || password.length() > str_max) {
            response.put("target", "password");
            response.put("message", "Password should be between %d and %d characters!".formatted(str_min, str_max));
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.addUserToDatabase(user);
            response.put("response", "success");
            response.put("message", "You was successfully registered. Now you can log in to your account!");
        }
        return response;
    }

    @PostMapping("/login_failure")
    @ResponseBody
    public Map<String, String> login_failure() {
        Map<String, String> response = new HashMap<>();
        response.put("response", "failure");
        response.put("message", "Invalid username or password");
        return response;
    }

    @PostMapping("/login_success")
    @ResponseBody
    public Map<String, String> login_success() {
        Map<String, String> response = new HashMap<>();
        response.put("response", "success");
        return response;
    }

}

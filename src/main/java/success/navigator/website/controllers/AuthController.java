package success.navigator.website.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import success.navigator.website.entities.User;
import success.navigator.website.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> request) {
        User user = new User();
        user.setUsername(request.get("username"));
        user.setPassword(request.get("password"));
        return userService.registerUser(user);
    }

    @PostMapping("/login_failure")
    public Map<String, String> login_failure() {
        Map<String, String> response = new HashMap<>();
        response.put("response", "failure");
        response.put("message", "Invalid username or password");
        return response;
    }

    @PostMapping("/login_success")
    public Map<String, String> login_success() {
        Map<String, String> response = new HashMap<>();
        response.put("response", "success");
        return response;
    }
}

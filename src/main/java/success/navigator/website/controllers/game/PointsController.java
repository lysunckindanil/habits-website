package success.navigator.website.controllers.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import success.navigator.website.services.UserService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointsController {
    private final UserService userService;

    @PostMapping("/count")
    private Map<String, String> addPointsToUser(@RequestBody Map<String, String> request, Principal principal) {
        if (request.containsKey("points")) {
            userService.addPointsToUser(principal.getName(), Integer.valueOf(request.get("points")));
            return request;
        }
        return null;
    }
}

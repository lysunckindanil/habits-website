package success.navigator.website.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Role;
import success.navigator.website.entities.User;
import success.navigator.website.repositories.RoleRepository;
import success.navigator.website.repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Map<String, String> registerUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        int str_max = 20;
        int str_min = 5;
        Map<String, String> response = new HashMap<>();
        response.put("response", "failure");

        if (userRepository.findByUsername(username) != null) {
            response.put("target", "username");
            response.put("message", "Sorry, username %s already exists!".formatted(username));
        } else if (username.length() < str_min || username.length() > str_max) {
            response.put("target", "username");
            response.put("message", "Username should be between %d and %d characters!".formatted(str_min, str_max));
        } else if (password.length() < str_min || password.length() > str_max) {
            response.put("target", "password");
            response.put("message", "Password should be between %d and %d characters!".formatted(str_min, str_max));
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<>(List.of(roleRepository.getByName("ROLE_USER"))));
            userRepository.save(user);
            response.put("response", "success");
            response.put("message", "You was successfully registered. Now you can log in to your account!");
        }
        return response;
    }

    public void deleteUserFromDatabase(Long id) {
        userRepository.deleteById(id);
    }

    // user points
    public void addPointsToUser(String username, Integer points) {
        User user = userRepository.findByUsername(username);
        user.setPoints(user.getPoints() + points);
        userRepository.save(user);
    }

    public void grantAdminRoleToUser(String username) {
        User user = findByUsername(username);
        user.getRoles().add(roleRepository.getByName("ROLE_ADMIN"));
        userRepository.save(user);
    }

    public void deleteAdminRoleToUser(String username) {
        User user = findByUsername(username);
        user.getRoles().removeIf(x -> x.getName().equals("ROLE_ADMIN"));
        userRepository.save(user);
    }

    // related to spring security
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }


}

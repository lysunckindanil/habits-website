package success.navigator.website.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import success.navigator.website.services.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(
                        // admin
                        "/admin/**",
                        // categories
                        "/categories/**",
                        // tasks
                        "/tasks/add", "/tasks/*/edit", "/tasks/*/delete",
                        // images
                        "/images/add", "/images/delete"
                ).hasRole("ADMIN"))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/tasks").hasRole("USER"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .httpBasic(withDefaults())
                .formLogin(form -> form.loginPage("/login").successForwardUrl("/login_success").failureForwardUrl("/login_failure").permitAll())
                .logout(form -> form.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}

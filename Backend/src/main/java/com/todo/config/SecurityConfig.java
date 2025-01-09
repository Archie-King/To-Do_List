package com.todo.config;

import com.todo.model.User;
import com.todo.service.UserService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;  // Password encoder initializes directly

    public SecurityConfig(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",      // Swagger Static Resource Path
                                "/swagger-ui.html",    // Swagger UI Path
                                "/v3/api-docs/**",     // OpenAPI Document Path
                                "/v3/api-docs.yaml",    // OpenAPI Document Path
                                "/api/users/register"  // Allow anonymous access to the registration interface
                        ).permitAll()
                        .anyRequest().authenticated()  // Other requests must be authenticated
                )
                .httpBasic(Customizer.withDefaults());  // Use HTTP Basic authentication
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(ObjectProvider<UserService> userServiceProvider) {
        return username -> {
            UserService userService = userServiceProvider.getObject();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().split(","))
                    .build();
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;  // Returns the initialized password encoder
    }

}

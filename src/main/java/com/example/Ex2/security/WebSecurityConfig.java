package com.example.Ex2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
    private final Environment env;

    public WebSecurityConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // Disable CSRF protection for simplicity in this example
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(request -> {
                                    String path = request.getRequestURI();
                                    // Permit access to GET /BooksAPI without authentication
                                    return path.equals("/BooksAPI") || path.equals("/BooksAPI/**") && request.getMethod().equals("GET");
                                }).permitAll()
                                // Require authentication for all other requests
                                .anyRequest().authenticated()
                )
                .httpBasic(); // Use HTTP Basic authentication
                http.csrf().disable() ;
                http.cors();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String username = env.getProperty("spring.security.user.name");
        String password = env.getProperty("spring.security.user.password");
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
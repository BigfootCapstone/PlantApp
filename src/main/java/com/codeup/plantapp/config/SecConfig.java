package com.codeup.plantapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
public class SecConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()
                .requestMatchers("/search","/users/*", "weather").authenticated()
                .requestMatchers("/search", "/users/*", "/plants/*", "/friends/*", "/register", "/users/login","/").permitAll()
                .requestMatchers("/css/**", "/js/**").permitAll()
        );

        http.formLogin((form) -> form.loginPage("/users/login").defaultSuccessUrl("/users/profile"));
        http.logout((form) -> form.logoutSuccessUrl("/"));
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
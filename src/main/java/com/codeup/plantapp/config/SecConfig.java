package com.codeup.plantapp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecConfig {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnDefaultWebSecurity
    static class SecurityFilterChainConfiguration {

        @Bean
        @Order(SecurityProperties.BASIC_AUTH_ORDER)
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
            http.formLogin(withDefaults());
            http.httpBasic(withDefaults());
            return http.build();
        }
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll()
        );

        http.formLogin((form) -> form.loginPage("/login").defaultSuccessUrl("/"));
        http.logout((form) -> form.logoutSuccessUrl("/login"));
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return NoOpPasswordEncoder.getInstance();
    }
}
package com.example.demo.configurations;

import com.example.demo.filters.JwtTokenFilter;
import com.example.demo.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(requests -> {
//                    requests.requestMatchers("**")
//                            .permitAll();                       // cho qua het
//                })
//                .build();
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
//                    requests.requestMatchers("**")
//                            .permitAll();
                    requests.requestMatchers(
                            String.format("%s/users/register", apiPrefix),
                            String.format("%s/users/login", apiPrefix),
                            String.format("%s/categories**", apiPrefix)
                    )
                            .permitAll()
//                            .requestMatchers(GET, String.format("%s/categories**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)  //GET thi ai vo cha duoc, can gi user hay admin
                            .requestMatchers(POST, String.format("%s/categories**", apiPrefix)).hasRole(Role.ADMIN) // 403 Forbidden: dang nhap nhung k du quyen
                            .requestMatchers(PUT, String.format("%s/categories**", apiPrefix)).hasRole( Role.ADMIN)
                            .requestMatchers(DELETE, String.format("%s/categories**", apiPrefix)).hasRole(Role.ADMIN)
                            .anyRequest().authenticated();
                });
        return httpSecurity.build();
    }
}

package com.roasteg.todo.config;

import com.roasteg.todo.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        try {
            httpSecurity
                    .csrf((csrf) -> csrf.disable())
                    .authorizeHttpRequests((httpRequests) -> {
                        httpRequests.requestMatchers("/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated();

                    });
            httpSecurity
                    .sessionManagement((sessionManagement) -> {
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    });
            httpSecurity
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSecurity.build();
    }
}

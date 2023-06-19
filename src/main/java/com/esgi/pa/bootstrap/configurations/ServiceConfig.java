package com.esgi.pa.bootstrap.configurations;

import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.esgi.pa.domain.services")
public class ServiceConfig {

    private final UserAdapter userAdapter;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String username) -> userAdapter.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

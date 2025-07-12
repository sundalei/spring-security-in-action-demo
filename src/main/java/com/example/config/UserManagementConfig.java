package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

  @Bean
  public UserDetailsService userDetailsService() {

    var userDetailsService = new InMemoryUserDetailsManager();

    var user = User.withUsername("john")
        .password("12345")
        .authorities("read")
        .passwordEncoder(s -> new Sha512PasswordEncoder().encode(s))
        .build();

    userDetailsService.createUser(user);
    return userDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Sha512PasswordEncoder();
  }
}

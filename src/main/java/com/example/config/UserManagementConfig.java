package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

  /**
   * Creates a UserDetailsService bean with an in-memory user.
   *
   * @return UserDetailsService instance with a predefined user.
   */
  @Bean
  UserDetailsService userDetailsService() {

    var userDetailsService = new InMemoryUserDetailsManager();

    var user = User.withUsername("john").password("12345").authorities("read").build();

    userDetailsService.createUser(user);
    return userDetailsService;
  }

  /**
   * Provides a PasswordEncoder bean that does not perform any encoding.
   *
   * @return PasswordEncoder instance that does not encode passwords.
   */
  @Bean
  @SuppressWarnings("deprecation")
  PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}

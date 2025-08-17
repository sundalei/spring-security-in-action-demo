package com.example.config;

import com.example.filters.AuthenticationLoggingFilter;
import com.example.filters.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebAuthorizationConfig {

  @Bean
  SecurityFilterChain configure(final HttpSecurity http) throws Exception {

    http.httpBasic(Customizer.withDefaults());

    http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

    http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
    http.addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class);

    return http.build();
  }
}

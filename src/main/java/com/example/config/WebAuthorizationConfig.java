package com.example.config;

import com.example.filters.AuthenticationLoggingFilter;
import com.example.filters.RequestValidationFilter;
import com.example.filters.StaticKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebAuthorizationConfig {

  private final StaticKeyAuthenticationFilter filter;

  public WebAuthorizationConfig(StaticKeyAuthenticationFilter filter) {
    this.filter = filter;
  }

  @Bean
  SecurityFilterChain configure(final HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

    http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
    http.addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class);
    http.addFilterAt(filter, BasicAuthenticationFilter.class);

    return http.build();
  }
}

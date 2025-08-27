package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class SpringSecurityInActionDemoApplication {

  /**
   * Entry point.
   *
   * @param args Parameters to configure this app
   */
  public static void main(final String[] args) {
    SpringApplication.run(SpringSecurityInActionDemoApplication.class, args);
  }
}

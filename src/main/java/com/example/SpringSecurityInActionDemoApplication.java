package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

@SpringBootApplication
public class SpringSecurityInActionDemoApplication {

  /**
   * Entry point.
   *
   * @param args Parameters to configure this app
   */
  public static void main(final String[] args) {
    SpringApplication.run(SpringSecurityInActionDemoApplication.class, args);
  }

  /**
   * Command line runner to generate a salt for password encoding.
   * @return CommandLineRunner instance
   */
  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
      StringKeyGenerator keyGenerator = KeyGenerators.string();
      String salt = keyGenerator.generateKey();
      System.out.println("Generated salt: " + salt);
    };
  }
}

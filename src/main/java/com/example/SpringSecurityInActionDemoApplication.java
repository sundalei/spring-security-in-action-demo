package com.example;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
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
   * Command line runner to demonstrate key generation.
   *
   * @return CommandLineRunner instance
   */
  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
      StringKeyGenerator keyGenerator = KeyGenerators.string();
      String salt = keyGenerator.generateKey();
      System.out.println("Generated salt: " + salt);

      BytesKeyGenerator bytesKeyGenerator = KeyGenerators.secureRandom();
      byte[] key = bytesKeyGenerator.generateKey();
      int keyLength = bytesKeyGenerator.getKeyLength();
      System.out.println("Generated key: " + key + ", Key Length: " + keyLength);

      BytesKeyGenerator sharedKeyGenerator = KeyGenerators.shared(16);
      byte[] key1 = sharedKeyGenerator.generateKey();
      byte[] key2 = sharedKeyGenerator.generateKey();
      System.out.println("Generated shared key 1: " + key1);
      System.out.println("Generated shared key 2: " + key2);
      System.out.println("Keys are equal: " + Arrays.equals(key1, key2));
    };
  }
}

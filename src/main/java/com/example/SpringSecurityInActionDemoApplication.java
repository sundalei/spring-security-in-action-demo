package com.example;

import com.example.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SpringSecurityInActionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityInActionDemoApplication.class, args);
	}

}

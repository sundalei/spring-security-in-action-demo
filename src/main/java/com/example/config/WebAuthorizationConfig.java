package com.example.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfig {

    private final RsaKeyProperties rsaKeyProperties;

    public WebAuthorizationConfig(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    @Order(1)
    SecurityFilterChain tokenFilterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable);

        // This security configuration ONLY applies to /token
        http.securityMatcher("/token");

        // All requests to /token must be authenticated
        http.authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
        );

        // Use Basic Authentication for /token
        http.httpBasic(Customizer.withDefaults());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(oauth2
                -> oauth2.jwt(Customizer.withDefaults()));

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(rsaKeyProperties.publicKey())
                .build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey())
                .privateKey(rsaKeyProperties.privateKey())
                .build();

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }
}

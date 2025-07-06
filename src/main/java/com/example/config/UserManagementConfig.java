package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

@Configuration
public class UserManagementConfig {

    private final ContextSource contextSource;

    public UserManagementConfig(ContextSource contextSource) {
        this.contextSource = contextSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        var ldapUserDetailsManager = new LdapUserDetailsManager(contextSource);
        ldapUserDetailsManager.setGroupSearchBase("ou=groups");
        ldapUserDetailsManager.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=groups", "uid"));

        return ldapUserDetailsManager;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

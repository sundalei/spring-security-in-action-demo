package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class StaticKeyAuthenticationFilter implements Filter {

  @Value("${authorization.key}")
  private String authorizationKey;

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    var httpRequest = (HttpServletRequest) servletRequest;
    var httpResponse = (HttpServletResponse) servletResponse;

    String authentication = httpRequest.getHeader("Authorization");

    if (authorizationKey.equals(authentication)) {

      // Create an Authentication object
      Authentication auth =
          new UsernamePasswordAuthenticationToken(
              "user", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
      // Set it in the SecurityContextHolder
      SecurityContextHolder.getContext().setAuthentication(auth);

      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}

package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationLoggingFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String requestId = request.getHeader("Request-Id");
    LOG.info("Successfully authenticated request with id {}", requestId);

    filterChain.doFilter(request, response);
  }
}

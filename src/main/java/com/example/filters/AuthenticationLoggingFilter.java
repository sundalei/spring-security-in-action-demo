package com.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationLoggingFilter implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestId = httpRequest.getHeader("Request-Id");
    LOG.info("Successfully authenticated request with id {}", requestId);

    chain.doFilter(request, response);
  }
}

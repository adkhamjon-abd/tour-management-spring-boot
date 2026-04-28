package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthenticationLoggingFilter implements Filter {

    private final Logger logger = Logger.getLogger(AuthenticationLoggingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String requestId = httpRequest.getHeader("x-mock-response-id");

        logger.info("AuthenticationLoggingFilter: Successfully authenticated request with id " + requestId);


        filterChain.doFilter(servletRequest, servletResponse);
    }
}

package com.weblab4.opilab3.JWT;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter which checks user's auth token
 */
@WebFilter("/secured/*")
public class JWTFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null)
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing");

        String token = authHeader.substring(7);
        try {
            Jwts.parser().setSigningKey(JWTUtil.SECRET_KEY).parseClaimsJws(token).getBody();
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired token");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

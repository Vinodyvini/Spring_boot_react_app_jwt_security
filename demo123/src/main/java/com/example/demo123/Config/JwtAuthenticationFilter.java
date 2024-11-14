package com.example.demo123.Config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");

	    if (token != null && token.startsWith("Bearer ")) {
	        token = token.substring(7); // Remove "Bearer " prefix
	        String username = jwtUtil.extractUsername(token);
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // Validate the token
	            if (jwtUtil.validateToken(token, username)) {
	                // Create an authentication token with the username
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                        username, null, new ArrayList<>()); // You can add authorities if needed
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }
	    }
	    filterChain.doFilter(request, response);

	}

}

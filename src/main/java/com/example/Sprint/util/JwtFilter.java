package com.example.Sprint.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * It intercepts incoming HTTP requests to check if they have a valid JWT.
 * If valid, it sets the authentication in the SecurityContext so that
 * Spring Security recognizes the user as logged in.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // Utility class to handle JWT creation, parsing, and validation
    private final JwtUtil jwtUtil;

    // Used to load user details from the database by username
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get the "Authorization" header from the request
        String authHeader = request.getHeader("Authorization");

        // If there is no token or it doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token part (after "Bearer ")
        String jwt = authHeader.substring(7);

        // Extract username from the token
        String username = jwtUtil.extractUsername(jwt);

        // Proceed only if username exists and user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details from DB using the username from the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token against user details (check expiration, signature, etc.)
            if (jwtUtil.isTokenValid(jwt, userDetails)) {

                // Create authentication object with the user's authorities
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Attach request-specific details (IP address, session ID, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Store authentication in the SecurityContext so Security knows the user is logged in
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue processing the request in the filter chain
        filterChain.doFilter(request, response);
    }

}

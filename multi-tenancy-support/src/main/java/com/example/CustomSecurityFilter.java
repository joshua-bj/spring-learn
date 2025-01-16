package com.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        // Access the HttpServletRequest
        String requestUri = request.getRequestURI();
        String parameters = mapToString(request.getParameterMap());
        String method = request.getMethod();
        System.out.println("Request URI: " + requestUri + ", Parameters: " + parameters + ", Method: " + method);

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    private String mapToString(Map<String, String[]> parameters){
        // Custom formatting using StringBuilder
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(Arrays.toString(entry.getValue())).append(", ");
        }

        // Remove trailing comma and space
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}

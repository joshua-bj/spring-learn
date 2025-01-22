package com.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        // Access the HttpServletRequest
        String requestUri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String parameters = Utils.mapToString(parameterMap);
        String method = request.getMethod();
        System.out.println("Request URI: " + requestUri + ", Parameters: " + parameters + ", Method: " + method);

        parameterMap.forEach((key, value) ->{
            if(key.equalsIgnoreCase("tenant")){
                request.getSession().setAttribute(Constants.TENANT_ID, value[0]);
            }
        });

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}

package com.oreumio.james.rest.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CORSFilter extends OncePerRequestFilter {

    private String corsAllowedOrigins;

    private String corsMaxAge;

    private String corsAllowedMethods;

    @Value("${cors.allowed.origins}")
    public void setCorsAllowedOrigins(String corsAllowedOrigins) {
        this.corsAllowedOrigins = corsAllowedOrigins;
    }

    @Value("${cors.max.age}")
    public void setCorsMaxAge(String corsMaxAge) {
        this.corsMaxAge = corsMaxAge;
    }

    @Value("${cors.allowed.methods}")
    public void setCorsAllowedMethods(String corsAllowedMethods) {
        this.corsAllowedMethods = corsAllowedMethods;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    		throws ServletException, IOException {
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(corsAllowedOrigins.split(",")));

        String originHeader = request.getHeader("Origin");
//        System.out.println("Origin=" + (originHeader == null ? "<null>" : originHeader));

        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {


//            if (allowedOrigins.contains(request.getHeader("Origin"))) {
                response.addHeader("Access-Control-Allow-Origin", "*");
//            }

            response.addHeader("Access-Control-Allow-Methods", corsAllowedMethods);
            response.addHeader("Access-Control-Allow-Headers", "content-type, Authorization, Cache-Control");
            response.addHeader("Access-Control-Max-Age", corsMaxAge);
        } else {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        filterChain.doFilter(request, response);
    }
}

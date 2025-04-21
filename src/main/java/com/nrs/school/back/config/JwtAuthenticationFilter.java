package com.nrs.school.back.config;

import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ENDPOINT_NOT_FOUND_MESSAGE = "Path %s not found";

    private final CustomExceptionResolver customExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RequestMappingHandlerMapping handlerMapping;

    public JwtAuthenticationFilter(
            CustomExceptionResolver customExceptionResolver,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver,
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping
    ) {
        this.customExceptionResolver = customExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        var endpoints = new ArrayList<String>();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
                RequestMappingInfo requestMappingInfo = entry.getKey();
                assert requestMappingInfo.getPathPatternsCondition() != null;
                endpoints.add(requestMappingInfo.getPathPatternsCondition().getPatterns().iterator().next().toString());
            }

            if (!endpoints.contains(request.getRequestURI())) {
                throw new ObjectNotFoundException(ENDPOINT_NOT_FOUND_MESSAGE.formatted(request.getRequestURI()));
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            customExceptionResolver.resolveException(request, response, exception, exception);
        }
    }
}

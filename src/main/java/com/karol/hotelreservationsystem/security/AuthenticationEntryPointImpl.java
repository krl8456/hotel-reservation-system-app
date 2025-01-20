package com.karol.hotelreservationsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karol.hotelreservationsystem.http.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        ApiErrorResponse<String> apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Authentication failed"
        );

        log.info(
                "Attempted unauthorized access: User '{}' attempted to access '{}' at {}",
                request.getRemoteUser(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        String responseBody = new ObjectMapper().writeValueAsString(apiErrorResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseBody);
    }
}

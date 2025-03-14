package com.karol.hotelreservationsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karol.hotelreservationsystem.http.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        ApiErrorResponse<String> apiErrorResponse = ApiErrorResponse.of(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "Access denied"
        );

        log.info(
                "Access denied for user: {} trying to access: {} at {}",
                request.getRemoteUser(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        String responseBody = new ObjectMapper().writeValueAsString(apiErrorResponse);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseBody);
    }
}



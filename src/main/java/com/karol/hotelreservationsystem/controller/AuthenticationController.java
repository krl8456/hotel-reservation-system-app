package com.karol.hotelreservationsystem.controller;

import com.karol.hotelreservationsystem.dto.SignInRequest;
import com.karol.hotelreservationsystem.dto.SignUpRequest;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost.localdomain:3000",
        methods = {RequestMethod.OPTIONS , RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody SignInRequest request) {
        String token = authenticationService.authenticate(request);

        ApiResponse<String> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "User logged in successfully",
                token
        );
        return ResponseEntity.ok(body);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody SignUpRequest request) {
        String token = authenticationService.register(request);

        ApiResponse<String> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "User created successfully",
                token
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<User>> getUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        User user = authenticationService.getUserByToken(jwtToken);

        ApiResponse<User> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "User retrieved successfully",
                user
        );

        return ResponseEntity.ok(body);
    }
}

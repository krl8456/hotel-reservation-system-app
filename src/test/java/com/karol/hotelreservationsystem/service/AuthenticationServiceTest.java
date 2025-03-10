package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.SignInRequest;
import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.repository.TokenRepository;
import com.karol.hotelreservationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void givenUser_whenAuthenticate_thenReturnsToken() {
        String email = "test@example.com";
        String password = "password";
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        SignInRequest request = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String response = authenticationService.authenticate(request);
        assertNotNull(response);
    }

    @Test
    public void givenNotCreatedUser_whenAuthenticate_thenThrowsAnException() {
        String email = "test@example.com";
        String password = "password";
        SignInRequest request = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.authenticate(request);
        });
    }
}
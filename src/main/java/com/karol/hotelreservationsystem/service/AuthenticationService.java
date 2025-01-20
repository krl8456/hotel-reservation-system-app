package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.SignInRequest;
import com.karol.hotelreservationsystem.dto.SignUpRequest;
import com.karol.hotelreservationsystem.model.Role;
import com.karol.hotelreservationsystem.model.Token;
import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.repository.TokenRepository;
import com.karol.hotelreservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Account with this email not found"));

        String token = jwtService.generateToken(user);
        revokeAllTokensByUser(user);
        saveUserToken(token, user);

        return token;
    }

    public String register(SignUpRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);
        saveUserToken(token, user);

        return token;
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokensByUser = tokenRepository.findAllTokensByUser(user.getId());

        if (!validTokensByUser.isEmpty()) {
            validTokensByUser.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(validTokensByUser);
        } else {
            log.info("No tokens found for user: {}", user.getId());
        }
    }

    private void saveUserToken(String token, User user) {
        Token tokenWithStatus = Token.builder()
                .token(token)
                .loggedOut(false)
                .user(user)
                .build();

        tokenRepository.save(tokenWithStatus);
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user;
    }
}

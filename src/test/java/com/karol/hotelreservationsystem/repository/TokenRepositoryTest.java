package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.Role;
import com.karol.hotelreservationsystem.model.Token;
import com.karol.hotelreservationsystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setupUser() {
        user = User.builder()
                .firstName("test")
                .lastName("test")
                .email("email@gmail.com")
                .password("password")
                .role(Role.ROLE_USER)
                .build();
        user = userRepository.save(user);
    }

    @Test
    public void givenToken_whenSave_thenReturnsSavedToken() {
        Token token = Token.builder()
                .token("Token")
                .loggedOut(false)
                .user(user)
                .build();

        Token savedToken = tokenRepository.save(token);

        assertNotNull(savedToken);
        assertTrue(savedToken.getId() > 0);
    }

    @Test
    public void givenTokens_whenFindAll_thenReturnsCorrectAmountOfTokens() {
        Token token1 = Token.builder()
                .token("Token1")
                .loggedOut(false)
                .user(user)
                .build();
        Token token2 = Token.builder()
                .token("Token2")
                .loggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token1);
        tokenRepository.save(token2);

        List<Token> savedTokens = tokenRepository.findAll();

        assertNotNull(savedTokens);
        assertEquals(2, savedTokens.size());
    }

    @Test
    public void givenToken_whenFindById_thenReturnsToken() {
        Token token = Token.builder()
                .token("Token")
                .loggedOut(false)
                .user(user)
                .build();

        tokenRepository.save(token);
        Token retrievedToken = tokenRepository.findById(token.getId())
                .orElseThrow(() -> new AssertionError("Token not found"));

        assertNotNull(retrievedToken);
    }

    @Test
    public void givenTokens_whenFindAllTokensByUser_thenReturnsTokensByUser() {
        Token token1 = Token.builder()
                .token("Token1")
                .loggedOut(false)
                .user(user)
                .build();
        Token token2 = Token.builder()
                .token("Token2")
                .loggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token1);
        tokenRepository.save(token2);

        List<Token> tokensByUser = tokenRepository.findAllTokensByUser(user.getId());

        assertNotNull(tokensByUser);
        assertEquals(2, tokensByUser.size());
    }

    @Test
    public void givenToken_whenFindByTokenValue_thenReturnsToken() {
        Token token = Token.builder()
                .token("Token")
                .loggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token);

        Token retrievedToken = tokenRepository.findByToken(token.getToken())
                .orElseThrow(() -> new AssertionError("Token not found"));

        assertNotNull(retrievedToken);
    }

    @Test
    public void givenToken_whenUpdate_thenReturnsUpdatedToken() {
        Token token = Token.builder()
                .token("Token")
                .loggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token);

        Token retrievedToken = tokenRepository.findById(token.getId())
                .orElseThrow(() -> new AssertionError("Token not found"));
        retrievedToken.setToken("newToken");
        Token updatedToken = tokenRepository.save(retrievedToken);

        assertNotNull(updatedToken);
        assertNotNull(updatedToken.getToken());
    }

    @Test
    public void givenToken_whenDelete_thenDeletesToken() {
        Token token = Token.builder()
                .token("Token")
                .loggedOut(false)
                .user(user)
                .build();

        tokenRepository.save(token);
        tokenRepository.deleteById(token.getId());
        Optional<Token> removalResult = tokenRepository.findById(token.getId());

        assertTrue(removalResult.isEmpty());
    }
}
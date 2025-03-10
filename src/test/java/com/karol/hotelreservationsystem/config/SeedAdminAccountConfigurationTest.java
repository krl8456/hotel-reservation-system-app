package com.karol.hotelreservationsystem.config;

import com.karol.hotelreservationsystem.init.AdminAccountSeeder;
import com.karol.hotelreservationsystem.model.Role;
import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SeedAdminAccountConfigurationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminAccountSeeder adminAccountSeeder;
    @Value("${admin.password}")
    private String adminPassword;
    @Test
    public void givenSeedAdminAccount_whenAdminDoNotExist_thenCreatesAdminUser() {
        userRepository.deleteAll();

        adminAccountSeeder.run();

        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());

        User admin = users.get(0);
        assertEquals("admin", admin.getFirstName());
        assertEquals("admin", admin.getLastName());
        assertEquals("admin@admin.com", admin.getEmail());
        assertEquals(Role.ROLE_ADMIN, admin.getRole());
        assertTrue(passwordEncoder.matches(adminPassword, admin.getPassword()));
    }

    @Test
    public void givenSeedAdminAccount_whenAdminExists_thenDoesntCreateAdminUser() {
        adminAccountSeeder.run();

        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
    }
}
package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

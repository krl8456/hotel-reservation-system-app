package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.Reservation;
import com.karol.hotelreservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    Optional<Reservation> findByIdAndUser(Long id, User user);
}
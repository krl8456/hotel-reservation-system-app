package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.City;
import com.karol.hotelreservationsystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCity(City city);
    Optional<Hotel> findByName(String name);
}
package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
    List<City> findByCountry(String country);
}
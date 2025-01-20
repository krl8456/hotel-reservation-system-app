package com.karol.hotelreservationsystem.init;

import com.karol.hotelreservationsystem.model.*;
import com.karol.hotelreservationsystem.repository.CityRepository;
import com.karol.hotelreservationsystem.repository.HotelRepository;
import com.karol.hotelreservationsystem.repository.HotelRoomRepository;
import com.karol.hotelreservationsystem.repository.UserRepository;
import com.karol.hotelreservationsystem.service.UserAccountDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:admin.properties")
public class DataSeeder implements CommandLineRunner {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountDetailsService userAccountDetailsService;
    @Value("${firstname}")
    private String firstName;
    @Value("${lastname}")
    private String lastName;
    @Value("${phoneNumber}")
    private String phoneNumber;
    @Value("${email}")
    private String email;
    @Value("${password}")
    private String password;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User
                    .builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userAccountDetailsService.save(admin);
            log.debug("created ADMIN user - {}", admin);
        }

        if (cityRepository.count() == 0) {
            List<City> cities = List.of(
                    new City("New York", "10001", "New York", "USA"),
                    new City("Los Angeles", "90001", "California", "USA"),
                    new City("Paris", "75000", "Île-de-France", "France"),
                    new City("Berlin", "10115", "Berlin", "Germany"),
                    new City("Tokyo", "100-0001", "Kantō", "Japan")
            );
            cityRepository.saveAll(cities);
            log.info("Cities seeded successfully");
        }

        if (hotelRepository.count() == 0) {
            List<City> cities = cityRepository.findAll();

            if (!cities.isEmpty()) {
                List<Hotel> hotels = List.of(
                        new Hotel("Grand Central Hotel", "123 Main St", 5, cities.get(0)),
                        new Hotel("Ocean View Resort", "456 Beach Ave", 4, cities.get(1)),
                        new Hotel("Eiffel Tower Hotel", "789 Eiffel St", 5, cities.get(2)),
                        new Hotel("Berlin Plaza Inn", "101 Berlin Rd", 4, cities.get(3)),
                        new Hotel("Tokyo Sky Hotel", "1-2-3 Shinjuku", 3, cities.get(4))
                );
                hotelRepository.saveAll(hotels);
                log.info("Hotels seeded successfully");
            } else {
                log.error("No cities found to associate with hotels.");
            }
        }

        if (hotelRoomRepository.count() == 0) {
            List<Hotel> hotels = hotelRepository.findAll();

            if (!hotels.isEmpty()) {
                List<HotelRoom> rooms = List.of(
                        new HotelRoom("101", 2, true, hotels.get(0)),
                        new HotelRoom("102", 4, true, hotels.get(0)),
                        new HotelRoom("201", 3, true, hotels.get(1)),
                        new HotelRoom("202", 2, false, hotels.get(1)),
                        new HotelRoom("301", 1, true, hotels.get(2)),
                        new HotelRoom("401", 5, true, hotels.get(3)),
                        new HotelRoom("402", 3, true, hotels.get(4))
                );
                hotelRoomRepository.saveAll(rooms);

                log.info("Hotel rooms seeded successfully");
            } else {
                log.error("No hotels found to associate with rooms.");
            }
        }
    }
}
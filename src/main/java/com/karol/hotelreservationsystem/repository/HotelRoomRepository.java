package com.karol.hotelreservationsystem.repository;

import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
    List<HotelRoom> findByHotel(Hotel hotel);
    boolean existsByHotelAndRoomNumber(Hotel hotel, String roomNumber);
    Optional<HotelRoom> findByRoomNumberAndHotel(String roomNumber, Hotel hotel);
}
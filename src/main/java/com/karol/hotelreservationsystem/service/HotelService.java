package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.HotelRequest;
import com.karol.hotelreservationsystem.dto.HotelRoomRequest;
import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.model.HotelRoom;
import com.karol.hotelreservationsystem.repository.HotelRepository;
import com.karol.hotelreservationsystem.repository.HotelRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
    }

    @Transactional
    public Hotel editHotelById(Long id, HotelRequest updatedHotelRequest) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        hotel.setName(updatedHotelRequest.getName());
        hotel.setAddress(updatedHotelRequest.getAddress());
        hotel.setStarRating(updatedHotelRequest.getStarRating());

        return hotelRepository.save(hotel);
    }

    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        hotelRepository.delete(hotel);
    }

    @Transactional
    public Hotel addHotelRoomsToHotel(Long id, List<HotelRoomRequest> hotelRoomRequests) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        for (HotelRoomRequest hotelRoomRequest : hotelRoomRequests) {
            if (hotelRoomRepository.existsByHotelAndRoomNumber(hotel, hotelRoomRequest.getRoomNumber())) {
                throw new IllegalArgumentException("Room with number " + hotelRoomRequest.getRoomNumber() +
                        " already exists in the hotel with ID " + id);
            }

            HotelRoom hotelRoom = HotelRoom.builder()
                    .roomNumber(hotelRoomRequest.getRoomNumber())
                    .available(hotelRoomRequest.isAvailable())
                    .capacity(hotelRoomRequest.getCapacity())
                    .hotel(hotel)
                    .build();
            hotelRoomRepository.save(hotelRoom);
        }
        return hotelRepository.save(hotel);
    }

    @Transactional
    public HotelRoom addSingleRoomToHotel(Long id, HotelRoomRequest hotelRoomRequest) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        var hotelRoom = HotelRoom.builder()
                .roomNumber(hotelRoomRequest.getRoomNumber())
                .available(hotelRoomRequest.isAvailable())
                .capacity(hotelRoomRequest.getCapacity())
                .hotel(hotel)
                .build();

        return hotelRoomRepository.save(hotelRoom);
    }

    public List<HotelRoom> getHotelRoomsByHotelId(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        return hotelRoomRepository.findByHotel(hotel);
    }

}
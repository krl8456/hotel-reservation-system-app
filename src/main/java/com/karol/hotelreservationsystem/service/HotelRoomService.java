package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.HotelRoomRequest;
import com.karol.hotelreservationsystem.model.HotelRoom;
import com.karol.hotelreservationsystem.repository.HotelRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelRoomService {
    private final HotelRoomRepository hotelRoomRepository;

    public List<HotelRoom> getAllHotelRooms() {
        return hotelRoomRepository.findAll();
    }

    public HotelRoom getHotelRoomById(Long id) {
        return hotelRoomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel Room Not Found"));
    }

    @Transactional
    public HotelRoom editHotelRoomById(Long id, HotelRoomRequest updatedHotelRoomRequest) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel Room Not Found"));

        hotelRoom.setRoomNumber(updatedHotelRoomRequest.getRoomNumber());
        hotelRoom.setAvailable(updatedHotelRoomRequest.isAvailable());
        hotelRoom.setCapacity(updatedHotelRoomRequest.getCapacity());

        return hotelRoomRepository.save(hotelRoom);
    }

    @Transactional
    public void deleteHotelRoomById(Long id) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel Room Not Found"));

        hotelRoomRepository.delete(hotelRoom);
    }
}
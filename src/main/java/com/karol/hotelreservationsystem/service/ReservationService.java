package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.ReservationRequest;
import com.karol.hotelreservationsystem.exception.RoomNotAvailableException;
import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.model.HotelRoom;
import com.karol.hotelreservationsystem.model.Reservation;
import com.karol.hotelreservationsystem.model.User;
import com.karol.hotelreservationsystem.repository.HotelRepository;
import com.karol.hotelreservationsystem.repository.HotelRoomRepository;
import com.karol.hotelreservationsystem.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRepository hotelRepository;
    private final AuthenticationService authenticationService;

    public Reservation findReservationById(Long id) {
        User user = authenticationService.getCurrentUser();
        Reservation reservation = reservationRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservation.setHotelName(reservation.getHotelRoom().getHotel().getName());
        reservation.setRoomNumber(reservation.getHotelRoom().getRoomNumber());

        return reservation;
    }

    public Reservation assignReservationToUser(ReservationRequest reservationRequest) {
        User user = authenticationService.getCurrentUser();

        Hotel hotel = hotelRepository.findByName(reservationRequest.getHotelName())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with this name not found"));

        HotelRoom hotelRoom = hotelRoomRepository.findByRoomNumberAndHotel(reservationRequest.getRoomNumber(), hotel)
                .orElseThrow(() -> new EntityNotFoundException("Hotel room with this number not found in the specified hotel"));

        if (!hotelRoom.isAvailable()) {
            throw new RoomNotAvailableException("This hotel room is not available");
        }

        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setUser(user);
        reservation.setHotelRoom(hotelRoom);
        reservation.setHotel(hotel);

        hotelRoom.setAvailable(false);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsFromUser() {
        User user = authenticationService.getCurrentUser();

        List<Reservation> reservations = reservationRepository.findByUser(user);

        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("Reservations not found");
        }

        for (Reservation reservation : reservations) {
            HotelRoom hotelRoom = reservation.getHotelRoom();
            String hotelName = hotelRoom.getHotel().getName();
            String room = hotelRoom.getRoomNumber();
            reservation.setHotelName(hotelName);
            reservation.setRoomNumber(room);
        }

        return reservations;
    }

    @Transactional
    public void deleteReservationById(Long id) {
        User user = authenticationService.getCurrentUser();
        Reservation reservation = reservationRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        HotelRoom hotelRoom = reservation.getHotelRoom();
        hotelRoom.setAvailable(true);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public Reservation editReservationById(Long id, ReservationRequest reservationRequest) {
        User user = authenticationService.getCurrentUser();
        Reservation reservation = reservationRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        Hotel previousHotel = hotelRepository.findByName(reservation.getHotel().getName())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with this name not found"));

        HotelRoom previousHotelRoom = hotelRoomRepository.findByRoomNumberAndHotel(reservation.getHotelRoom().getRoomNumber(), previousHotel)
                .orElseThrow(() -> new EntityNotFoundException("Hotel room with this number not found in the specified hotel"));

        previousHotelRoom.setAvailable(true);

        Hotel hotel = hotelRepository.findByName(reservationRequest.getHotelName()).orElseThrow(() -> new EntityNotFoundException("Hotel with this name not found"));

        HotelRoom hotelRoom = hotelRoomRepository.findByRoomNumberAndHotel(reservationRequest.getRoomNumber(), hotel)
                .orElseThrow(() -> new EntityNotFoundException("Hotel room with this number not found in the specified hotel"));

        if (!hotelRoom.isAvailable()) {
            throw new RoomNotAvailableException("This hotel room is not available");
        }

        previousHotelRoom.setAvailable(true);

        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setHotelName(reservation.getHotelName());
        reservation.setRoomNumber(reservation.getRoomNumber());
        reservation.setHotel(hotel);
        reservation.setHotelRoom(hotelRoom);
        hotelRoom.setAvailable(false);

        return reservation;
    }
}
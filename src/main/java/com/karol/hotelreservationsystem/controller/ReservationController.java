package com.karol.hotelreservationsystem.controller;

import com.karol.hotelreservationsystem.dto.ReservationRequest;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.model.Reservation;
import com.karol.hotelreservationsystem.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost.localdomain:3000",
        methods = {RequestMethod.OPTIONS , RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Reservation>> assignReservationToCurrentUser(
            @Valid @RequestBody ReservationRequest reservationRequest
    ) {
        Reservation reservation = reservationService.assignReservationToUser(reservationRequest);
        String location = "/reservations/" + reservation.getId();

        ApiResponse<Reservation> body = ApiResponse.withLocation(
                HttpStatus.CREATED.value(),
                "Reservation added successfully",
                location
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsFromUser() {
        List<Reservation> reservations = reservationService.getReservationsFromUser();

        ApiResponse<List<Reservation>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Reservations found",
                reservations
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Reservation>> getReservationsFromUserById(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);

        ApiResponse<Reservation> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Reservation found",
                reservation
        );
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Reservation>> editReservationFromUserById(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest reservationRequest
    ) {
        Reservation reservation = reservationService.editReservationById(id, reservationRequest);

        ApiResponse<Reservation> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Reservation updated successfully",
                reservation
        );
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> deleteReservationFromUserById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);

        ApiResponse<Void> body = ApiResponse.withNoContent(
                HttpStatus.NO_CONTENT.value(),
                "Reservation deleted successfully"
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }
}
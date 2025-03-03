package com.karol.hotelreservationsystem.controller;

import com.karol.hotelreservationsystem.dto.HotelRoomRequest;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.model.HotelRoom;
import com.karol.hotelreservationsystem.service.HotelRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotelRooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost.localdomain:3000",
        methods = {RequestMethod.OPTIONS , RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HotelRoom>>> getAllHotelRooms() {
        List<HotelRoom> hotelRooms = hotelRoomService.getAllHotelRooms();

        ApiResponse<List<HotelRoom>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel rooms found",
                hotelRooms
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelRoom>> getHotelRoomById(@PathVariable Long id) {
        HotelRoom hotelRoom = hotelRoomService.getHotelRoomById(id);

        ApiResponse<HotelRoom> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel room found",
                hotelRoom
        );
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HotelRoom>> editHotelRoomById(
            @PathVariable Long id,
            @Valid @RequestBody HotelRoomRequest updatedHotelRoomRequest
    ) {
        HotelRoom hotelRoom = hotelRoomService.editHotelRoomById(id, updatedHotelRoomRequest);

        ApiResponse<HotelRoom> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel room updated",
                hotelRoom
        );
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteHotelRoomById(@PathVariable Long id) {
        hotelRoomService.deleteHotelRoomById(id);

        ApiResponse<Void> body = ApiResponse.withNoContent(
                HttpStatus.NO_CONTENT.value(),
                "Hotel room deleted"
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }
}
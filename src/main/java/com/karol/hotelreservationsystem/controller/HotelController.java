package com.karol.hotelreservationsystem.controller;

import com.karol.hotelreservationsystem.dto.HotelRequest;
import com.karol.hotelreservationsystem.dto.HotelRoomRequest;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.model.HotelRoom;
import com.karol.hotelreservationsystem.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.OPTIONS , RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Hotel>>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();

        ApiResponse<List<Hotel>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotels found",
                hotels
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Hotel>> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);

        ApiResponse<Hotel> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel found",
                hotel
        );
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Hotel>> editHotelById(
            @PathVariable Long id,
            @Valid @RequestBody HotelRequest updatedHotelRequest
    ) {
        Hotel hotel = hotelService.editHotelById(id, updatedHotelRequest);

        ApiResponse<Hotel> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel edited successfully",
                hotel
        );
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotelById(id);

        ApiResponse<Void> body = ApiResponse.withNoContent(
                HttpStatus.NO_CONTENT.value(),
                "Hotel deleted successfully"
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }

    @PostMapping("/{id}/hotelRooms/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addHotelRoomsToHotel(
            @PathVariable Long id,
            @Valid @RequestBody List<HotelRoomRequest> hotelRoomRequests
    ) {
        hotelService.addHotelRoomsToHotel(id, hotelRoomRequests);
        String location = "/hotels/" + id + "/rooms";

        ApiResponse<String> body = ApiResponse.withLocation(
                HttpStatus.CREATED.value(),
                "Hotel room added successfully",
                location
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/{id}/hotelRooms")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HotelRoom>> addSingleRoomToHotel(
            @PathVariable Long id,
            @Valid @RequestBody HotelRoomRequest hotelRoomRequest
    ) {
        HotelRoom hotelRoom = hotelService.addSingleRoomToHotel(id, hotelRoomRequest);
        String location = "/hotels/" + id + "/rooms/" + hotelRoom.getId();

        ApiResponse<HotelRoom> body = ApiResponse.withLocation(
                HttpStatus.CREATED.value(),
                "Hotel room added successfully",
                location
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/{id}/hotelRooms")
    public ResponseEntity<ApiResponse<List<HotelRoom>>> getHotelRoomsByHotelId(@PathVariable Long id) {
        List<HotelRoom> hotelRooms = hotelService.getHotelRoomsByHotelId(id);

        ApiResponse<List<HotelRoom>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotels found",
                hotelRooms
        );
        return ResponseEntity.ok(body);
    }
}

package com.karol.hotelreservationsystem.controller;

import com.karol.hotelreservationsystem.dto.CityRequest;
import com.karol.hotelreservationsystem.dto.HotelRequest;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.model.City;
import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.service.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("api/v1/cities")
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.OPTIONS , RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addCity(@Valid @RequestBody CityRequest city) {
        City addedCity = cityService.save(city);
        String location = "/cities/" + addedCity.getId();

        ApiResponse<String> body = ApiResponse.withLocation(
                HttpStatus.CREATED.value(),
                "City added successfully",
                location
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<City>>> getAllCities() {
        List<City> cities = cityService.getAll();

        ApiResponse<List<City>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Cities found",
                cities
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/countries/{country}")
    public ResponseEntity<ApiResponse<List<City>>> getCitiesByCountry(@PathVariable String country) {
        List<City> cities = cityService.getByCountry(country);

        ApiResponse<List<City>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Cities found",
                cities
        );
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<City>> editCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest updatedCityRequest
    ) {
        City editedCity = cityService.editById(id, updatedCityRequest);

        ApiResponse<City> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "City updated successfully",
                editedCity
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<City>> getCityById(@PathVariable Long id) {
        City city = cityService.getById(id);

        ApiResponse<City> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "City found",
                city
        );
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCityById(@PathVariable Long id) {
        cityService.deleteCityById(id);

        ApiResponse<Void> body = ApiResponse.withNoContent(
                HttpStatus.NO_CONTENT.value(),
                "City deleted successfully"
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }

    @PostMapping("/{cityId}/hotels/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addHotelsToCity(
            @PathVariable Long cityId,
            @RequestBody List<HotelRequest> hotelRequests
    ) {
        List<Hotel> hotels = cityService.addHotelsToCity(cityId, hotelRequests);
        String locations = hotels.stream()
                .map(hotel -> "/cities/" + cityId + "/hotels/" + hotel.getId())
                .collect(Collectors.joining(", "));

        ApiResponse<String> body = ApiResponse.withLocation(
                HttpStatus.CREATED.value(),
                "Hotels added successfully",
                locations
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/{id}/hotels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addSingleHotelToCity(
            @PathVariable Long id,
            @Valid @RequestBody HotelRequest hotelRequest
    ) {
        Hotel addedHotel = cityService.addSingleHotelToCity(id, hotelRequest);
        String location = "/cities/" + id + "/hotels/" + addedHotel.getId();

        ApiResponse<String> body = ApiResponse.withData(
                HttpStatus.CREATED.value(),
                "Hotel added successfully",
                location
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/{id}/hotels")
    public ResponseEntity<ApiResponse<List<Hotel>>> getHotelsByCityId(@PathVariable Long id) {
        List<Hotel> hotels = cityService.getHotelsByCityId(id);

        ApiResponse<List<Hotel>> body = ApiResponse.withData(
                HttpStatus.OK.value(),
                "Hotel found",
                hotels
        );
        return ResponseEntity.ok(body);
    }
}
package com.karol.hotelreservationsystem.service;

import com.karol.hotelreservationsystem.dto.CityRequest;
import com.karol.hotelreservationsystem.dto.HotelRequest;
import com.karol.hotelreservationsystem.model.City;
import com.karol.hotelreservationsystem.model.Hotel;
import com.karol.hotelreservationsystem.repository.CityRepository;
import com.karol.hotelreservationsystem.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;

    public City save(CityRequest request) {
        var city = City.builder()
                .name(request.getName())
                .postalCode(request.getPostalCode())
                .region(request.getRegion())
                .country(request.getCountry()).build();

        return cityRepository.save(city);
    }

    public List<City> getAll() {
        List<City> cities = cityRepository.findAll();

        if (cities.isEmpty()) {
            throw new EntityNotFoundException("No cities found");
        }
        return cities;
    }

    public City getByName(String cityName) {
        return cityRepository.findByName(cityName).orElseThrow(() -> new EntityNotFoundException("No city found"));
    }

    public List<City> getByCountry(String country) {
        List<City> cities = cityRepository.findByCountry(country);

        if (cities.isEmpty()) {
            throw new EntityNotFoundException("No cities found");
        }
        return cities;
    }

    public City getById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));
    }

    @Transactional
    public City editById(Long id, CityRequest updatedCityRequest) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));

        city.setName(updatedCityRequest.getName());
        city.setPostalCode(updatedCityRequest.getPostalCode());
        city.setRegion(updatedCityRequest.getRegion());
        city.setCountry(updatedCityRequest.getCountry());

        return cityRepository.save(city);
    }

    @Transactional
    public void deleteCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));

        cityRepository.delete(city);
    }

    @Transactional
    public List<Hotel> addHotelsToCity(Long id, List<HotelRequest> hotelRequests) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));

        List<Hotel> hotels = new ArrayList<>();

        for (HotelRequest hotelRequest : hotelRequests) {
            var hotel = Hotel.builder()
                    .name(hotelRequest.getName())
                    .address(hotelRequest.getAddress())
                    .starRating(hotelRequest.getStarRating())
                    .city(city)
                    .build();
            hotelRepository.save(hotel);
            hotels.add(hotel);
        }

        return hotels;
    }

    @Transactional
    public Hotel addSingleHotelToCity(Long id, HotelRequest hotelRequest) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));

        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .starRating(hotelRequest.getStarRating())
                .city(city)
                .build();

        return hotelRepository.save(hotel);
    }

    public List<Hotel> getHotelsByCityId(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No city found"));

        return hotelRepository.findByCity(city);
    }
}
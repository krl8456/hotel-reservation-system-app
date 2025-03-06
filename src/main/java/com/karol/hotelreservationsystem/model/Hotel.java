package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "hotels")
//@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String address;
    private int starRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cities_id")
    @JsonIgnore
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelRoom> hotel_rooms = new ArrayList<>();

    public Hotel(String name, String address, int starRating, City city) {
        this.name = name;
        this.address = address;
        this.starRating = starRating;
        this.city = city;
    }

    // Poprzednio wzorzec Builder został zaimplementowany przy pomocy adnotacji @Builder z biblioteki Lombok
    public static HotelBuilder builder() {
        return new HotelBuilder();
    }

    public static class HotelBuilder {
        private Long id;
        private String name;
        private String address;
        private int starRating;
        private City city;
        private List<HotelRoom> hotel_rooms;

        HotelBuilder() {
        }

        public HotelBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public HotelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HotelBuilder address(String address) {
            this.address = address;
            return this;
        }

        public HotelBuilder starRating(int starRating) {
            this.starRating = starRating;
            return this;
        }

        @JsonIgnore
        public HotelBuilder city(City city) {
            this.city = city;
            return this;
        }

        @JsonIgnore
        public HotelBuilder hotel_rooms(List<HotelRoom> hotel_rooms) {
            this.hotel_rooms = hotel_rooms;
            return this;
        }

        public Hotel build() {
            return new Hotel(this.id, this.name, this.address, this.starRating, this.city, this.hotel_rooms);
        }

        public String toString() {
            return "Hotel.HotelBuilder(id=" + this.id + ", name=" + this.name + ", address=" + this.address + ", starRating=" + this.starRating + ", city=" + this.city + ", hotel_rooms=" + this.hotel_rooms + ")";
        }
    }
}
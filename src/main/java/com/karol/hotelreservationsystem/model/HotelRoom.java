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
@Table(name = "hotel_rooms")
//@Builder
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String roomNumber;
    private int capacity;
    @Column(columnDefinition = "BIT")
    private boolean available;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_id")
    @JsonIgnore
    private Hotel hotel;

    @JsonIgnore
    @OneToMany(mappedBy = "hotelRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public HotelRoom(String roomNumber, int capacity, boolean available, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.available = available;
        this.hotel = hotel;
    }

    // Poprzednio wzorzec Builder został zaimplementowany przy pomocy adnotacji @Builder z biblioteki Lombok
    public static HotelRoomBuilder builder() {
        return new HotelRoomBuilder();
    }

    public static class HotelRoomBuilder {
        private Long id;
        private String roomNumber;
        private int capacity;
        private boolean available;
        private Hotel hotel;
        private List<Reservation> reservations;

        HotelRoomBuilder() {
        }

        public HotelRoomBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public HotelRoomBuilder roomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public HotelRoomBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public HotelRoomBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        @JsonIgnore
        public HotelRoomBuilder hotel(Hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        @JsonIgnore
        public HotelRoomBuilder reservations(List<Reservation> reservations) {
            this.reservations = reservations;
            return this;
        }

        public HotelRoom build() {
            return new HotelRoom(this.id, this.roomNumber, this.capacity, this.available, this.hotel, this.reservations);
        }

        public String toString() {
            return "HotelRoom.HotelRoomBuilder(id=" + this.id + ", roomNumber=" + this.roomNumber + ", capacity=" + this.capacity + ", available=" + this.available + ", hotel=" + this.hotel + ", reservations=" + this.reservations + ")";
        }
    }
}
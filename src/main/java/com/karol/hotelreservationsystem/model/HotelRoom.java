package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "hotel_rooms")
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
}
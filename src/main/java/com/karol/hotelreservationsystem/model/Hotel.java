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
@Table(name = "hotels")
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
}
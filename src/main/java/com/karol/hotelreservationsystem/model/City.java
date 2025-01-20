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
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String postalCode;
    private String region;
    private String country;

    public City(String name, String postalCode, String region, String country) {
        this.name = name;
        this.postalCode = postalCode;
        this.region = region;
        this.country = country;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotels = new ArrayList<>();
}
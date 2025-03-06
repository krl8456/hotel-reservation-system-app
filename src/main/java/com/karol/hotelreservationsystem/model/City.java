package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
//@Builder
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

    // Poprzednio wzorzec Builder został zaimplementowany przy pomocy adnotacji @Builder z biblioteki Lombok
    public static CityBuilder builder() {
        return new CityBuilder();
    }

    public static class CityBuilder {
        private Long id;
        private String name;
        private String postalCode;
        private String region;
        private String country;
        private List<Hotel> hotels;

        CityBuilder() {
        }

        public CityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CityBuilder region(String region) {
            this.region = region;
            return this;
        }

        public CityBuilder country(String country) {
            this.country = country;
            return this;
        }

        @JsonIgnore
        public CityBuilder hotels(List<Hotel> hotels) {
            this.hotels = hotels;
            return this;
        }

        public City build() {
            return new City(this.id, this.name, this.postalCode, this.region, this.country, this.hotels);
        }

        public String toString() {
            return "City.CityBuilder(id=" + this.id + ", name=" + this.name + ", postalCode=" + this.postalCode + ", region=" + this.region + ", country=" + this.country + ", hotels=" + this.hotels + ")";
        }
    }
}
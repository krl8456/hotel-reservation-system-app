package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startDate;
    private String endDate;
    private LocalDateTime reservationDate;

    @Transient
    private String hotelName;

    @Transient
    private String roomNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_rooms_id")
    @JsonIgnore
    private HotelRoom hotelRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_rooms_hotels_id")
    @JsonIgnore
    private Hotel hotel;
}
package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "reservations")
//@Builder
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

    // Poprzednio wzorzec Builder został zaimplementowany przy pomocy adnotacji @Builder z biblioteki Lombok
    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    public static class ReservationBuilder {
        private Long id;
        private String startDate;
        private String endDate;
        private LocalDateTime reservationDate;
        private String hotelName;
        private String roomNumber;
        private User user;
        private HotelRoom hotelRoom;
        private Hotel hotel;

        ReservationBuilder() {
        }

        public ReservationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReservationBuilder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationBuilder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationBuilder reservationDate(LocalDateTime reservationDate) {
            this.reservationDate = reservationDate;
            return this;
        }

        public ReservationBuilder hotelName(String hotelName) {
            this.hotelName = hotelName;
            return this;
        }

        public ReservationBuilder roomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        @JsonIgnore
        public ReservationBuilder user(User user) {
            this.user = user;
            return this;
        }

        @JsonIgnore
        public ReservationBuilder hotelRoom(HotelRoom hotelRoom) {
            this.hotelRoom = hotelRoom;
            return this;
        }

        @JsonIgnore
        public ReservationBuilder hotel(Hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        public Reservation build() {
            return new Reservation(this.id, this.startDate, this.endDate, this.reservationDate, this.hotelName, this.roomNumber, this.user, this.hotelRoom, this.hotel);
        }

        public String toString() {
            return "Reservation.ReservationBuilder(id=" + this.id + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", reservationDate=" + this.reservationDate + ", hotelName=" + this.hotelName + ", roomNumber=" + this.roomNumber + ", user=" + this.user + ", hotelRoom=" + this.hotelRoom + ", hotel=" + this.hotel + ")";
        }
    }
}
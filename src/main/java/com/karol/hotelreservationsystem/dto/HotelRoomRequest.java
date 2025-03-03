package com.karol.hotelreservationsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomRequest {
    @Valid

    @NotBlank(message = "{room-number.required}")
    private String roomNumber;
    private boolean available;
    @Digits(integer = 2, fraction = 0, message = "{capacity.size}")
    private int capacity;
}
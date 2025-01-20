package com.karol.hotelreservationsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class HotelRequest {
    @Valid

    @NotBlank(message = "{hotel-name.required}")
    private String name;
    @NotBlank(message = "{hotel-address.required}")
    private String address;
    @NotNull(message = "{star-rating.required}")
    @Min(value = 1, message = "{star-rating.size-min}")
    @Max(value = 5, message = "{star-rating.size-max")
    private int starRating;
}
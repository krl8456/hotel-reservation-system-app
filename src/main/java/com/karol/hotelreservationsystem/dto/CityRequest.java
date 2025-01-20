package com.karol.hotelreservationsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {
    @Valid

    @NotBlank(message = "{city.required}")
    private String name;
    @NotNull(message = "{postal-code.required}")
    @Size(min = 5, max = 10, message = "{region.required}")
    private String postalCode;
    @NotBlank(message = "{region.required}")
    private String region;
    @NotBlank(message = "{country.required}")
    private String country;
}
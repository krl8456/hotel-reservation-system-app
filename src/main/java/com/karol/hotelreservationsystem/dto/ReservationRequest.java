package com.karol.hotelreservationsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @Valid

    @NotBlank(message = "{start-date.required}")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$",
            message = "{start-date.pattern}")
    private String startDate;

    @NotBlank(message = "{end-date.required}")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$",
            message = "{end-date.pattern}")
    private String endDate;

    @NotBlank(message = "{hotel-name.required}")
    private String hotelName;

    @NotBlank(message = "{room-number.required}")
    private String roomNumber;
}
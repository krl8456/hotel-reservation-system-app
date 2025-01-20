package com.karol.hotelreservationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid.format}")
    private String email;
    @NotBlank(message = "{password.required}")
    @Size(min = 5, max = 40, message = "{password.invalid.length}")
    private String password;
}

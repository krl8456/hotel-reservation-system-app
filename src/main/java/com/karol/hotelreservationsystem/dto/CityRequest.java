package com.karol.hotelreservationsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    // Tydzień 1, Wzorzec Builder 1
    // Jest to wzorzec konstrukcyjny umożliwiający tworzenie obiektów, przydatny gdy z góry nie wiemy ile będziemy inicjalizować
    // pól w przypadku inicjalizacji. Bez tego musielibyśmy tworzyć wszystkie możliwe konstruktory co nie było by komfortowe
    // Koniec, Tydzień 1, Wzorzec Builder 1
    public static CityRequestBuilder builder() {
        return new CityRequestBuilder();
    }

    public static class CityRequestBuilder {
        private @Valid
        @NotBlank(message = "{city.required}") String name;
        private @NotNull(message = "{postal-code.required}")
        @Size(min = 5, max = 10, message = "{region.required}") String postalCode;
        private @NotBlank(message = "{region.required}") String region;
        private @NotBlank(message = "{country.required}") String country;

        CityRequestBuilder() {
        }

        public CityRequestBuilder name(@Valid @NotBlank(message = "{city.required}") String name) {
            this.name = name;
            return this;
        }

        public CityRequestBuilder postalCode(@NotNull(message = "{postal-code.required}") @Size(min = 5, max = 10, message = "{region.required}") String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public CityRequestBuilder region(@NotBlank(message = "{region.required}") String region) {
            this.region = region;
            return this;
        }

        public CityRequestBuilder country(@NotBlank(message = "{country.required}") String country) {
            this.country = country;
            return this;
        }

        public CityRequest build() {
            return new CityRequest(this.name, this.postalCode, this.region, this.country);
        }

        public String toString() {
            return "CityRequest.CityRequestBuilder(name=" + this.name + ", postalCode=" + this.postalCode + ", region=" + this.region + ", country=" + this.country + ")";
        }
    }
}
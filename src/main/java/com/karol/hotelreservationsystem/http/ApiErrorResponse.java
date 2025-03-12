package com.karol.hotelreservationsystem.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse<T> {
    private ApiErrorResponseMessageDetails<T> error;

    // Tydzień 1, Wzorzec fabryka 2
    // W tym przypadku jest przedstawiona implementacja tzw. fabryki statycznej za pomocą metody
    // statycznej of. Upraszcza ona tworzenie obiektów oraz dodaje dzięki nazwie pewnych informacji jak dany obiekt jest tworzony.
    // Koniec, Tydzień 1, Wzorzec fabryka 2
    public static <T> ApiErrorResponse<T> of(int code, String message, T details) {
        ApiErrorResponseMessageDetails<T> errorDetails = new ApiErrorResponseMessageDetails<>(code, message, details);

        return new ApiErrorResponse<>(errorDetails);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ApiErrorResponseMessageDetails<T> {
        private int code;
        private String message;
        private T details;
    }
}
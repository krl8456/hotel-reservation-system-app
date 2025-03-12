package com.karol.hotelreservationsystem.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    int code;
    String message;
    @Nullable T data;
    @Nullable String location;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message, String location) {
        this.code = code;
        this.message = message;
        this.location = location;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Tydzień 1, Wzorzec fabryka 3
    // W tym przypadku jest przedstawiona implementacja tzw. fabryki statycznej za pomocą metod
    // statycznych withData, withLocation, withNoContent. Upraszcza ona tworzenie obiektów oraz dodaje dzięki nazwie pewnych informacji jak dany obiekt jest tworzony.
    // Koniec, Tydzień 1, Wzorzec fabryka 3
    public static <T> ApiResponse<T> withData(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<T> withLocation(int code, String message, String location) {
        return new ApiResponse<>(code, message, location);
    }

    public static ApiResponse<Void> withNoContent(int code, String message) {
        return new ApiResponse<>(code, message);
    }
}

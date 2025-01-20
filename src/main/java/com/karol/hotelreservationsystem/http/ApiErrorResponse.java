package com.karol.hotelreservationsystem.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse<T> {
    private ApiErrorResponseMessageDetails<T> error;

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
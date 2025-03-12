package com.karol.hotelreservationsystem.factory;
public class ErrorLogger extends Logger{
    private static final ErrorLogger instance = new ErrorLogger();

    private ErrorLogger() {}

    public static ErrorLogger getInstance() {
        return instance;
    }

    public void log(String message) {
        log.error(message);
    }
}

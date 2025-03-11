package com.karol.hotelreservationsystem.factory;

public class ErrorLogger extends Logger{
    private static final ErrorLogger instance = new ErrorLogger();

    private ErrorLogger(String someClass) {
        super(someClass);
    }

    private ErrorLogger() {}

    public static ErrorLogger getInstance(String someClass) {
        return new ErrorLogger(someClass);
    }

    public void log(String message) {
        log.error(message);
    }
}

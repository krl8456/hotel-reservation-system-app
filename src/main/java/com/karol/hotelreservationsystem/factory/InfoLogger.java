package com.karol.hotelreservationsystem.factory;

public class InfoLogger extends Logger{
    private static final InfoLogger instance = new InfoLogger();

    private InfoLogger(String someClass) {
        super(someClass);
    }

    private InfoLogger() {}

    public static InfoLogger getInstance(String someClass) {
        return new InfoLogger(someClass);
    }

    public void log(String message) {
        log.info(message);
    }
}

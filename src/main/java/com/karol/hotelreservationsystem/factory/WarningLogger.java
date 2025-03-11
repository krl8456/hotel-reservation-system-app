package com.karol.hotelreservationsystem.factory;

public class WarningLogger extends Logger{
    private static final WarningLogger instance = new WarningLogger();

    private WarningLogger(String someClass) {
        super(someClass);
    }

    private WarningLogger() {}

    public static WarningLogger getInstance(String someClass) {
        return new WarningLogger(someClass);
    }

    public void log(String message) {
        log.info(message);
    }
}

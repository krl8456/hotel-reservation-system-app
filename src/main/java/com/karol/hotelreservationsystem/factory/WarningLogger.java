package com.karol.hotelreservationsystem.factory;

public class WarningLogger extends Logger{
    private static final WarningLogger instance;

    private WarningLogger() {}

    static {
        instance = new WarningLogger();
    }

    public static WarningLogger getInstance() {
        return instance;
    }

    public void log(String message) {
        log.warn(message);
    }
}

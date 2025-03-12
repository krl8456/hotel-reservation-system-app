package com.karol.hotelreservationsystem.factory;

public class InfoLogger extends Logger{
    private InfoLogger() {}

    private static class SingletonHolder {
        private static final InfoLogger INSTANCE = new InfoLogger();
    }

    public static InfoLogger getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void log(String message) {
        log.info(message);
    }
}

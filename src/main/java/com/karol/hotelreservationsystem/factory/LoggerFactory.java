package com.karol.hotelreservationsystem.factory;

public class LoggerFactory {
    public static Logger getLogger(String type) {
        if (type.equals("ERROR")) {
            return ErrorLogger.getInstance();
        }
        else if (type.equals("WARN")) {
            return WarningLogger.getInstance();
        }
        else {
            return InfoLogger.getInstance();
        }
    }
}

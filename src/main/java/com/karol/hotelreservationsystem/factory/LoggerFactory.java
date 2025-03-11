package com.karol.hotelreservationsystem.factory;

import org.springframework.stereotype.Component;

public class LoggerFactory {
    public static Logger getLogger(String someClass, String type) {
        Logger logger = null;
        if (type.equals("ERROR")) {
            return InfoLogger.getInstance(someClass);
        }
        else if (type.equals("WARN")) {
            return WarningLogger.getInstance(someClass);
        }
        else {
            return ErrorLogger.getInstance(someClass);
        }
    }
}

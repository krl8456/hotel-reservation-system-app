package com.karol.hotelreservationsystem.factory;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Logger {
    private static String someClass;
    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(someClass);

    protected Logger(String someClass) {
        Logger.someClass = someClass;
    }

    protected static void setSomeClass(String someClass) {
        Logger.someClass = someClass;
    }

    protected static String getSomeClass() {
        return someClass;
    }
}

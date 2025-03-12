package com.karol.hotelreservationsystem.factory;

// Tydzień 1, Wzorzec singleton 3
// Użycie wzorca singleton gwarantuje, że w aplikacji będzie tylko jedna instancja danej klasy
// W tym przypadku korzystając z singletona możemy poprawić wydajność aplikacji oraz zunifikować konfiguracje logowania
// Tydzień 1, Wzorzec singleton 3
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

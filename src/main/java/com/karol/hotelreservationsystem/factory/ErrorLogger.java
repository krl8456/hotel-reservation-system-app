package com.karol.hotelreservationsystem.factory;
// Tydzień 1, Wzorzec singleton 1
// Użycie wzorca singleton gwarantuje, że w aplikacji będzie tylko jedna instancja danej klasy
// W tym przypadku korzystając z singletona możemy poprawić wydajność aplikacji oraz zunifikować konfiguracje logowania
// Tydzień 1, Wzorzec singleton 1
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

package com.karol.hotelreservationsystem.factory;
// Tydzień 1, Wzorzec singleton 2
// Użycie wzorca singleton gwarantuje, że w aplikacji będzie tylko jedna instancja danej klasy
// W tym przypadku korzystając z singletona możemy poprawić wydajność aplikacji oraz zunifikować konfiguracje logowania
// Tydzień 1, Wzorzec singleton 2
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

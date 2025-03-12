package com.karol.hotelreservationsystem.factory;

// Tydzień 1, Wzorzec Factory 1
// Wzorzec ten został użyty w projekcie, ponieważ używamy różnego typu logowania w aplikacji, fabryka stanowi
// centralne źródło ich tworzenia co ułatwia z nimi prace gdyż nowe typy logowania możemy dodawać tylko w tym jednym miejscu
// Koniec, Tydzień 1, Wzorzec Factory 1
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

package smarthome.controller;

/**
 * Die 'Model' im SmartHome-System. Enthält eine Sammlung
 * von Räumen (Klasse {@link Room}) und eine Reihe von Methoden,
 * welche die SmartHome-Funktionalität implementieren.
 */
public class HomeModel {
    private final Room[] rooms;

    public HomeModel(Room[] rooms) {
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }
}

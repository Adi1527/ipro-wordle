package smarthome.controller;

/**
 * Repräsentiert ein Zimmer im SmartHome. Ein Zimmer besteht aus
 * einem Namen und einer Sammlung von Lampen (Klasse {@link Lamp}).
 */
public class Room {
    private final String name;
    private final Lamp[] lamps;

    public Room(String name, Lamp[] lamps) {
        this.name = name;
        this.lamps = lamps;
    }

    public String getName() {
        return name;
    }

    public Lamp[] getLamps() {
        return lamps;
    }

    @Override
    public String toString() {
        return name + " (" + lamps.length + " lamps)";
    }
}

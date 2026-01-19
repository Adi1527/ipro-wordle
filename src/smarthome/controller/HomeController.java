package smarthome.controller;

import java.awt.*;

import ch.mvcbase.ControllerBase;

public class HomeController extends ControllerBase<HomeModel> {

    /**
     * The Controller needs a Model.
     */
    public HomeController() {
        super(createHome());
    }

    public void switchLamps(String key){
        async(() -> {
            if("0123456".contains(key)){
                switchLamps(Integer.parseInt(key));
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void switchLamps(int room) {
        async(() -> {
            Room r = model.getRooms()[room];
            for (Lamp lamp : r.getLamps()) {
                lamp.toggle();
            }
        });
    }

    private static HomeModel createHome() {
        Lamp[] kitchenLamps = {
                new Lamp("Ceiling Lamp", 2.5)};
        Room kitchen = new Room("Kitchen", kitchenLamps);
        Lamp[] livingRoomLamps = {
                new Lamp("Table Lamp", 7.0),
                new Lamp("Ceiling Lamp", 13.6),
                new Lamp("Niche Lamp", 5.2)};
        Room livingRoom = new Room("Living Room", livingRoomLamps);
        Lamp[] tvRoomLamps = {
                new Lamp("TV Lamp", 8.1),
                new Lamp("Ceiling Lamp", 12.7)};
        Room tvRoom = new Room("TV Room", tvRoomLamps);
        Lamp[] hallwayLamps = {
                new Lamp("Ceiling Lamp", 22.4),
                new Lamp("Sofa Lamp", 7.8),
                new Lamp("Entry Lamp", 6.6),
                new Lamp("Mirror Lamp 1", 3.5),
                new Lamp("Mirror Lamp 2", 3.8)};
        Room hallway = new Room("Hallway", hallwayLamps);
        Lamp[] bedroom1Lamps = {
                new Lamp("Ceiling Lamp", 10.1),
                new Lamp("Desk Lamp", 5.1)};
        Room bedroom1 = new Room("Bedroom 1", bedroom1Lamps);
        Lamp[] bedroom2Lamps = {
                new Lamp("Ceiling Lamp", 12.3)};
        Room bedroom2 = new Room("Bedroom 2", bedroom2Lamps);
        Lamp[] bedroom3Lamps = {
                new Lamp("Ceiling Lamp", 18.0),
                new Lamp("Bed Lamp 1", 2.2),
                new Lamp("Bed Lamp 2", 2.3)};
        Room bedroom3 = new Room("Bedroom 3", bedroom3Lamps);
        Room[] rooms = {
                kitchen, livingRoom, tvRoom, hallway,
                bedroom1, bedroom2, bedroom3};

        return new HomeModel(rooms);
    }
}

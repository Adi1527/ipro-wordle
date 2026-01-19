package smarthome.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

import smarthome.controller.HomeModel;
import smarthome.controller.Lamp;
import smarthome.controller.Room;

public class HomePanel implements Drawable {

    private final HomeModel model;

    public HomePanel(HomeModel model) {
        this.model = model;
    }

    @Override
    public void draw(Gui gui) {
        gui.setAlpha(1);
        gui.setColor(9, 9, 9);
        gui.fillRect(0, 0, gui.getWidth(), gui.getHeight());
        gui.drawImage("img/smarthome/home.png", 0, 0, HomeGUI.SCALE);
        for (Room room : model.getRooms()) {
            for (Lamp lamp : room.getLamps()) {
                if (lamp.isOn()) {
                    gui.setAlpha(lamp.getBrightness());
                    String combined = room.getName() + " " + lamp.getName();
                    String img = combined.replace(' ', '-').toLowerCase();
                    gui.drawImage("img/smarthome/" + img + ".png", 0, 0, HomeGUI.SCALE);
                }
            }
        }
    }
}

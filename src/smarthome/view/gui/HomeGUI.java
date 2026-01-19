package smarthome.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Button;

import ch.mvcbase.ComponentGuiBase;
import smarthome.controller.HomeController;
import smarthome.controller.HomeModel;


public class HomeGUI extends ComponentGuiBase<HomeModel, HomeController> {
    static final int IMG_WIDTH = 463;
    static final int IMG_HEIGHT = 328;
    static final double SCALE = 2;

    private Button[] buttons;

    public HomeGUI(HomeController controller, int frameRate) {
        super(controller, "SmartHome", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);
    }

    @Override
    protected Component[] createComponents(HomeModel model) {
        var margin = 7 * SCALE;
        var x = margin;
        var y = IMG_HEIGHT * SCALE;
        var width = 58 * SCALE;
        var height = 18 * SCALE;

        Component[] components = new Component[8];
        components[0] = new smarthome.view.gui.HomePanel(model);

        buttons = new Button[7];
        for (int i = 0; i < 7; i++) {
            String roomName = model.getRooms()[i].getName();
            buttons[i] = new Button(roomName, false, x, y, width, height);
            buttons[i].setBackgroundColor(new Color(195, 182, 101));
            buttons[i].setHoveredBackgroundColor(new Color(219, 215, 134));
            x += width + margin;
            components[i + 1] = buttons[i];
        }
        return components;
    }

    @Override
    public void setupEventHandler(HomeController controller) {
        for (int i = 0; i < buttons.length; i++) {
            int buttonNum = i;
            buttons[i].setOnClick((x, y) -> controller.switchLamps(buttonNum));
        }

        setOnKeyReleased((key) -> controller.switchLamps(key));
    }

}

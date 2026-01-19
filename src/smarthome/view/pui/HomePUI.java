package smarthome.view.pui;

import com.pi4j.catalog.components.base.PIN;
import com.pi4j.catalog.components.SimpleButton;
import com.pi4j.catalog.components.SimpleLed;

import ch.mvcbase.PuiBase;
import trick17examples.smarthome.controller.HomeController;
import trick17examples.smarthome.controller.HomeModel;

public class HomePUI extends PuiBase<HomeModel, HomeController> {
    //declare all hardware components attached to RaspPi
    private SimpleLed led;
    private SimpleButton button;

    public HomePUI(HomeController controller, int frameRate) {
        super(controller, frameRate);
    }

    @Override
    public void initializeComponents(HomeModel model) {
        led    = new SimpleLed(pi4J, PIN.D22);
        button = new SimpleButton(pi4J, PIN.D24, false);
    }

    @Override
    public void setupEventHandler(HomeController controller) {
        button.onUp(() -> controller.switchLamps(1));
    }

    @Override
    public void updateComponents(HomeModel model) {
        if (model.getRooms()[1].getLamps()[0].isOn()) {
            led.on();
        } else {
            led.off();
        }
    }
}

package smarthome;


import smarthome.controller.HomeController;
import smarthome.view.gui.HomeGUI;
import smarthome.view.pui.HomePUI;

public class SmartHomeApp {
    private static final int FRAME_RATE = 50;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");

        HomeController controller = new HomeController();

        HomeGUI gui = new HomeGUI(controller, FRAME_RATE);
        HomePUI pui = new HomePUI(controller, FRAME_RATE);


        //gui.setFullScreen(true);
        gui.proceedUntilClosed();

        //cleanup when Gui is closed
        controller.shutdown();
        pui.shutdown();
    }
}

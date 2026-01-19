package wordle.view.gui;

import ch.mvcbase.ComponentGuiBase;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Button;
import wordle.controller.WordleController;
import wordle.controller.WordleModel;

public class WordleGUI extends ComponentGuiBase<WordleModel, WordleController> {
    public static final double IMG_WIDTH = 463;
    public static final double IMG_HEIGHT = 328;
    public static final double SCALE = 2;

    private Button[] buttons;

    public WordleGUI(WordleController controller, int frameRate) {
        super(controller, "SmartHome", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);

    }

    @Override
    protected Component[] createComponents(WordleModel model) {
        Component[] components = new Component[3];
        components[0] = new Background();
        components[1] = new Gridshadow();
        components[2] = new Grid5x6();
        return components;
    }
}

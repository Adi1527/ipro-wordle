package wordle.view.gui;

import ch.mvcbase.ComponentGuiBase;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Button;
import ch.trick17.gui.widget.Label;
import wordle.controller.WordleController;
import wordle.controller.WordleModel;

public class WordleGUI extends ComponentGuiBase<WordleModel, WordleController> {
    public static final double IMG_WIDTH = 600;
    public static final double IMG_HEIGHT = 397;
    public static final double SCALE = 2;

    Label[][] label = new Label[6][5];

    public void setupEventHandler(WordleController controller){

    }



    private Button[] buttons;

    public WordleGUI(WordleController controller, int frameRate) {
        super(controller, "SmartHome", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);

    }

    @Override
    protected Component[] createComponents(WordleModel model) {
        Component[] components = new Component[4];
        components[0] = new Background();
        components[1] = new Gridshadow();
        components[2] = new Grid5x6();
        components[3] = new Tastatur();
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                label[i][j] = new Label()
            }
        }
        return components;
    }
}

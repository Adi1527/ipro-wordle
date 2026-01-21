package wordle.view.gui;

import ch.mvcbase.ComponentGuiBase;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Label;
import wordle.controller.WordleController;
import wordle.controller.WordleModel;

public class WordleGUI extends ComponentGuiBase<WordleModel, WordleController> {
    public static final double IMG_WIDTH = 600;
    public static final double IMG_HEIGHT = 397;
    public static final double SCALE = 2;

    Label[][] label;

    public WordleGUI(WordleController controller, int frameRate) {
        label = new Label[6][5];
        super(controller, "Wordle", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);
    }

    @Override
    public void setupEventHandler(WordleController controller){
        setOnKeyReleased(key -> {
            if (key.equals("back_space")) {
                controller.handleBackspace();
            } else if (key.equals("enter")) {
                controller.handleEnter();
            } else if (key.length() == 1) {
                controller.handleKeyInput(key.charAt(0));
            }
        });
    }

    @Override
    protected Component[] createComponents(WordleModel model) {
        Component[] components = new Component[34];
        components[0] = new Background();
        components[1] = new Gridshadow();
        components[2] = new Grid5x6();
        components[3] = new Tastatur();
        int index = 4;
        int y = 27;
        for (int i = 0; i < 6; i++){
            int x = 490;
            for (int j = 0; j < 5; j++){
                label[i][j] = new Label(model.getLetter(i,j), x, y, 50);
                label[i][j].setTextAlignCenter();
                components[index] = label[i][j];
                index++;
                x += 54;
            }
            y += 57;
        }
        return components;
    }

    @Override
    public void updateComponents(WordleModel model) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                label[i][j].setText(model.getLetter(i, j));
            }
        }
    }
}
package wordle.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

public class Gridshadow implements Drawable {
    @Override
    public void draw(Gui gui) {
        gui.setColor(225,225,225);
        int y = -30;
        int x = 0;
        for (int i = 0; i < 6; i++){
            y += 57;
            x = 465;
            for (int j = 0; j < 5; j++){
                gui.fillRect(x,y,50,50);
                x += 54;
            }
        }
    }
}

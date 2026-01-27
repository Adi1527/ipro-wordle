package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

public class Grid5x6 implements Drawable {

    @Override
    public void draw(Gui gui) {
    gui.setColor(new Color(0,0,0));
    int y = -30;
    int x = 0;
    for (int i = 0; i < 6; i++){
        y += 57;
        x = 475;
        for (int j = 0; j < 5; j++){
            gui.drawRect(x,y,50,50);
            x += 54;
        }
    }
    }
}


package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

public class Grid5x6 implements Drawable {

    @Override
    public void draw(Gui gui) {
    gui.setColor(new Color(255,255,255));
    int y = 10;
    int x = 375;
    for (int i = 0; i < 6; i++){
        y += 60;
        x = 375;
        for (int j = 0; j < 5; j++){
            gui.drawRect(x,y,50,50);
            x += 50;
        }
    }
    }
}

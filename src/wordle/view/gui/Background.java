package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

import static wordle.view.gui.WordleGUI.*;

public class Background implements Drawable {


    @Override
    public void draw(Gui gui) {
        double radius = 880;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < 25; i++){
            gui.setColor(new Color(r,g,b));
            gui.fillCircle(600,200,radius);
            radius -= 35.2;
            r += 10;
            b += 10;
            g += 10;
        }
    }
}

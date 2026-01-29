package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

import static wordle.view.gui.WordleGUI.*;

public class Background implements Drawable {

    @Override
    public void draw(Gui gui) {
        double width = IMG_WIDTH * SCALE;
        double height = (IMG_HEIGHT + 28) * SCALE;
        double radius = 880;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < 255; i++){
            gui.setColor(new Color(r,g,b));
//            gui.fillRect(0,0,width,height);
            gui.fillCircle(600,200,radius);
            width -= 1;
            height -= 1;
            radius -= 3.8;
            r += 1;
            b += 1;
            g += 1;
        }
    }
}

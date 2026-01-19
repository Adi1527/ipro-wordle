package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

import static wordle.view.gui.WordleGUI.*;

public class Background implements Drawable {

    @Override
    public void draw(Gui gui) {
        gui.setColor(new Color(46,26,71));
        gui.fillRect(0,0,IMG_WIDTH*SCALE,(IMG_HEIGHT+28)*SCALE);
    }
}

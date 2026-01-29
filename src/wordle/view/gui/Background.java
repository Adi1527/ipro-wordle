package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

import static wordle.view.gui.WordleGUI.*;

public class Background implements Drawable {

    @Override
    public void draw(Gui gui) {
        gui.drawImage("resources/img/background.png",-170,0);
    }
}

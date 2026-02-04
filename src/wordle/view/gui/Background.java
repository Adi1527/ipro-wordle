package wordle.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

/**
 * Zeichnet den Hintergrund und den Titel
 */
public class Background implements Drawable {
    @Override
    public void draw(Gui gui) {
        gui.drawImage("resources/img/background.png",-170,0);
        gui.drawImageCentered("resources/img/titel.png",607,90,0.38);
    }
}

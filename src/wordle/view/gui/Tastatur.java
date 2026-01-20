package wordle.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;
import ch.trick17.gui.widget.Button;
import ch.trick17.gui.widget.Label;

public class Tastatur implements Drawable {

    @Override
    public void draw(Gui gui) {
        int y = 500;
        int x = 300;
        int seiten = 50;
        for (int i = 0; i < 10; i++){
            gui.drawRect(x,y,seiten,seiten);
            x += 60;
        }
        y += 60;
        x = 330;
        for (int i = 0; i < 9; i++){
            gui.drawRect(x,y,seiten,seiten);
            x += 60;
        }
        y += 60;
        x = 390;
        for (int i = 0; i < 7; i++){
            gui.drawRect(x,y,seiten,seiten);
            x += 60;
        }
    }

}

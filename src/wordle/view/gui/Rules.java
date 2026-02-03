package wordle.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

public class Rules implements Drawable {
    private boolean visible = false;

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void draw(Gui gui){
        if (visible){
            gui.drawImageCentered("resources/img/rules.png",600,389,0.8);
        }

    }
}

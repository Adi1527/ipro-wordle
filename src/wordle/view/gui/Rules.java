package wordle.view.gui;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

/**
 * Zeichnet das Regelfenster
 */
public class Rules implements Drawable {
    private boolean visible = false;

    /**
     * setzt den boolean auf true
     * @param visible boolean welcher geändert wird.
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }

    /**
     * zeichnet das Fenster effektiv
     * @param gui zeichnet das Fenster im Gui
     */
    public void draw(Gui gui){
        if (visible){
            gui.drawImageCentered("resources/img/rules.png",600,395,0.8);
        }

    }
}

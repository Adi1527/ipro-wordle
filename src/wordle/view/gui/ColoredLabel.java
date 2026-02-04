package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.widget.Label;

/**
 * Ist eine bearbeitete Version des Labels, welches farben ermöglicht.
 */
public class ColoredLabel extends Label {
    private Color backgroundColor = new Color(255, 255, 255);
    private static final double BOX_SIZE = 50;

    /**
     * Konstruktor für das ColoredLabel
     * @param text Text welcher angezeigt wird im Label
     * @param x die x position des Labels
     * @param y die y position des Labels
     * @param height die grösse des Labels
     */
    public ColoredLabel(String text, double x, double y, double height) {
        super(text, x, y, height);
    }

    /**
     * verändert die hintergrundfarbe des Labels
     * @param color die Farbe welche gebraucht wird
     */
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    /**
     * zeichnet das label mit der Farbe und dem Buchstaben
     * @param gui
     */
    @Override
    public void draw(Gui gui) {
        // Hintergrund-Box zeichnen
        gui.setColor(backgroundColor);
        gui.fillRect(getX() - BOX_SIZE / 2, getY(), BOX_SIZE, BOX_SIZE);

        // Text darüber zeichnen
        super.draw(gui);
    }
}

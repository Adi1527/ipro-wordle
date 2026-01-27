package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.widget.Label;

public class ColoredLabel extends Label {
    private Color backgroundColor = new Color(255, 255, 255);
    private static final int BOX_SIZE = 50;

    public ColoredLabel(String text, double x, double y, double height) {
        super(text, x, y, height);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public void draw(Gui gui) {
        // Hintergrund-Box zeichnen
        gui.setColor(backgroundColor);
        gui.fillRect(getX() - BOX_SIZE / 2, getY(), BOX_SIZE, BOX_SIZE);

        // Text darüber zeichnen
        super.draw(gui);
    }
}

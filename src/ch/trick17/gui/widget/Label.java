package ch.trick17.gui.widget;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;

import static java.util.Objects.requireNonNull;

public class Label extends Widget {

    public static final Color DEFAULT_TEXT_COLOR = new Color(0, 0, 0);

    private String text;
    private Color textColor = DEFAULT_TEXT_COLOR;
    private int textAlign = -1;

    public Label(String text, double x, double y, double height) {
        super(x, y, 10, height);
        this.text = requireNonNull(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = requireNonNull(text);
    }

    public void setTextAlignCenter(){
        textAlign = 0;
    }

    public void setTextAlignLeft(){
        textAlign = -1;
    }

    public void setTextAlignRight(){
        textAlign = 1;
    }

    public double getTextWidth(Gui gui) {
        return gui.stringWidth(text, getFamilyName(), getFontSize(), isBold(), isItalic());
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    @Override
    public void draw(Gui gui) {
        super.draw(gui);
        gui.setColor(textColor);

        gui.setTextAlign(textAlign);
        gui.drawString(text, getX(), getY() + getHeight() * 0.70);
    }
}

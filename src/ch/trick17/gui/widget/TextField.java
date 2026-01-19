package ch.trick17.gui.widget;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.EventListener;
import ch.trick17.gui.component.Rectangle;

import static ch.trick17.gui.impl.GuiBase.CHAR_UNDEFINED;
import static java.util.Objects.requireNonNull;

public class TextField extends Widget implements EventListener {

    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color DEFAULT_TEXT_COLOR = new Color(0, 0, 0);
    private static final Color DEFAULT_BORDER_COLOR = new Color(178, 188, 203);
    private static final Color DEFAULT_FOCUSSED_BORDER_COLOR = new Color(41, 127, 213);

    private String text = "";

    private Color backgroundColor     = DEFAULT_BACKGROUND_COLOR;
    private Color textColor           = DEFAULT_TEXT_COLOR;
    private Color borderColor         = DEFAULT_BORDER_COLOR;
    private Color focussedBorderColor = DEFAULT_FOCUSSED_BORDER_COLOR;

    private boolean focussed = false;

    public TextField(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = requireNonNull(text);
        onTextChange(text);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getFocussedBorderColor() {
        return focussedBorderColor;
    }

    public void setFocussedBorderColor(Color focussedBorderColor) {
        this.focussedBorderColor = focussedBorderColor;
    }

    public boolean isFocussed() {
        return focussed;
    }

    @Override
    public void onMouseButtonRelease(double x, double y, boolean left) {
        focussed = new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(x, y);
    }

    @Override
    public void onKeyRelease(String keyName, char keyChar) {
        var oldText = text;
        if (focussed) {
            if (keyName.equals("back_space")) {
                if (!text.isEmpty()) {
                    text = text.substring(0, text.length() - 1);
                }
            } else if (keyChar != CHAR_UNDEFINED) {
                text += keyChar;
            }
        }
        if (!text.equals(oldText)) {
            onTextChange(text);
        }
    }

    protected void onTextChange(String text) {
        // do nothing by default; subclasses can override
    }

    @Override
    public void draw(Gui gui) {
        super.draw(gui);

        gui.setColor(backgroundColor);
        gui.fillRect(getX(), getY(), getWidth(), getHeight());

        gui.setColor(focussed ? focussedBorderColor : borderColor);
        gui.setStrokeWidth(2);
        gui.setRoundStroke(true);
        gui.drawRect(getX(), getY(), getWidth(), getHeight());

        gui.setColor(textColor);
        gui.drawString(text, getX() + 5, getY() + getHeight() * 0.70);
    }
}

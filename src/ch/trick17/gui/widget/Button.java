package ch.trick17.gui.widget;

import java.util.Objects;
import java.util.function.BiConsumer;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Clickable;
import ch.trick17.gui.component.Hoverable;
import ch.trick17.gui.component.Rectangle;

import static java.util.Objects.requireNonNull;

public class Button extends Widget implements Hoverable, Clickable {

    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(41, 127, 213);
    public static final Color DEFAULT_TEXT_COLOR = new Color(255, 255, 255);
    public static final Color DEFAULT_HOVERED_BACKGROUND_COLOR = new Color(31, 95, 160);
    public static final Color DEFAULT_HOVERED_TEXT_COLOR = DEFAULT_TEXT_COLOR;

    private final boolean isCircle;

    private String text;

    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    private Color textColor = DEFAULT_TEXT_COLOR;
    private Color hoveredBackgroundColor = DEFAULT_HOVERED_BACKGROUND_COLOR;
    private Color hoveredTextColor = DEFAULT_HOVERED_TEXT_COLOR;

    private boolean hovered = false;

    private BiConsumer<Double, Double> onClick = (x, y) -> {};

    public Button(String text, boolean isCircle, double x, double y, double width, double height) {
        super(x, y, width, height);

        this.text = requireNonNull(text);
        this.isCircle = isCircle;
    }

    public void setOnClick(BiConsumer<Double, Double> action) {
        Objects.requireNonNull(action);
        onClick = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = requireNonNull(text);
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

    public Color getHoveredBackgroundColor() {
        return hoveredBackgroundColor;
    }

    public void setHoveredBackgroundColor(Color hoveredBackgroundColor) {
        this.hoveredBackgroundColor = hoveredBackgroundColor;
    }

    public Color getHoveredTextColor() {
        return hoveredTextColor;
    }

    public void setHoveredTextColor(Color hoveredTextColor) {
        this.hoveredTextColor = hoveredTextColor;
    }

    public boolean isHovered() {
        return hovered;
    }

    @Override
    public Rectangle getInteractiveArea(Gui gui) {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onMouseEnter() {
        hovered = true;
    }

    @Override
    public void onMouseExit() {
        hovered = false;
    }

    @Override
    public final void onLeftClick(double x, double y) {
        onClick.accept(x, y);
    }

    @Override
    public final void onRightClick(double x, double y) {
        // do nothing by default; subclasses can override
    }

    @Override
    public void draw(Gui gui) {
        super.draw(gui);

        gui.setColor(hovered ? hoveredBackgroundColor : backgroundColor);
        if(isCircle){
            gui.fillCircle(getX() + getWidth() * 0.5, getY() + getHeight() * 0.5,  getHeight() * 0.5);
        }
        else {
            gui.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        gui.setColor(hovered ? hoveredTextColor : textColor);
        gui.setTextAlignCenter();
        gui.drawString(text, getX() + getWidth() * 0.5, getY() + getHeight() * 0.70);
    }
}

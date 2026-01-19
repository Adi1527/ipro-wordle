package ch.trick17.gui.widget;

import java.awt.*;
import java.util.Arrays;

import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;

public abstract class Widget implements Drawable {
    private static final String[] AVAILABLE_FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    private double x;
    private double y;

    private double width;
    private double height;

    private String  familyName;
    private boolean bold = false;
    private boolean italic = false;

    protected Widget(double x, double y, double width, double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must be non-negative");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setFamilyName("Dialog");
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        if(Arrays.stream(AVAILABLE_FONTS).noneMatch(name -> name.equals(familyName))){
            throw new IllegalArgumentException("unknown FontFamily : " + familyName);
        }
        this.familyName = familyName;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be non-negative");
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height < 0) {
            throw new IllegalArgumentException("height must be non-negative");
        }
        this.height = height;
    }

    public int getFontSize() {
        return (int) (getHeight() / 2);
    }

    @Override
    public void draw(Gui gui) {
        gui.setFontFamily(getFamilyName());
        gui.setFontSize(getFontSize());
        gui.setBold(isBold());
        gui.setItalic(isItalic());
    }
}

package wordle.view.gui;

import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Drawable;
import wordle.controller.WordleModel;

public class ColoredKeyboard implements Drawable {

    private static final String ROW1 = "QWERTYUIOP";
    private static final String ROW2 = "ASDFGHJKL";
    private static final String ROW3 = "ZXCVBNM";

    private WordleModel model;

    public ColoredKeyboard(WordleModel model) {
        this.model = model;
    }

    @Override
    public void draw(Gui gui) {
        int y = 625;
        int x = 300;
        int size = 50;

        // Reihe 1
        for (int i = 0; i < ROW1.length(); i++){
            drawKey(gui, x, y, size, ROW1.charAt(i));
            x += 60;
        }

        // Reihe 2
        y += 60;
        x = 330;
        for (int i = 0; i < ROW2.length(); i++){
            drawKey(gui, x, y, size, ROW2.charAt(i));
            x += 60;
        }

        // Reihe 3
        y += 60;
        x = 390;
        for (int i = 0; i < ROW3.length(); i++){
            drawKey(gui, x, y, size, ROW3.charAt(i));
            x += 60;
        }
    }

    private void drawKey(Gui gui, int x, int y, int size, char letter) {
        // Farbe basierend auf Status holen
        int status = model.getKeyStatus(letter);
        Color bgColor = getColorForStatus(status);
        Color textColor;
        if (status == 0) {
            textColor = new Color(0, 0, 0);  // Schwarz
        } else {
            textColor = new Color(255, 255, 255);  // Weiß
        }

        // Hintergrund
        gui.setColor(bgColor);
        gui.fillRect(x, y, size, size);

        // Rahmen
        gui.setColor(new Color(50, 50, 50));
        gui.setStrokeWidth(2);
        gui.drawRect(x, y, size, size);

        // Buchstabe
        gui.setColor(textColor);
        gui.setTextAlignCenter();
        gui.setFontSize(20);
        gui.setBold(true);
        gui.drawString(String.valueOf(letter), x + size / 2.0, y + size * 0.70);
    }

    private Color getColorForStatus(int status) {
        switch (status) {
            case 1:  // Grün - richtige Position
                return new Color(34, 139, 34);
            case -1: // Orange - im Wort, falsche Position
                return new Color(255, 165, 0);
            case -2: // grau - nicht im Wort
                return new Color(0, 0, 0);
            default: // 0 - noch nicht benutzt
                return new Color(225, 225, 225);
        }
    }
}
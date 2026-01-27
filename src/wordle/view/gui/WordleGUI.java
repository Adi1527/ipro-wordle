package wordle.view.gui;

import ch.mvcbase.ComponentGuiBase;
import ch.trick17.gui.Color;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Label;
import wordle.controller.WordleController;
import wordle.controller.WordleModel;

public class WordleGUI extends ComponentGuiBase<WordleModel, WordleController> {
    public static final double IMG_WIDTH = 600;
    public static final double IMG_HEIGHT = 397;
    public static final double SCALE = 2;

    ColoredLabel[][] label;  // ← Geändert von Label zu ColoredLabel
    Label messageLabel;
    Label infoLabel;
    Label wrongInput;

    public WordleGUI(WordleController controller, int frameRate) {
        label = new ColoredLabel[6][5];  // ← Initialisierung VOR super()
        super(controller, "Wordle", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);
    }

    @Override
    public void setupEventHandler(WordleController controller){
        setOnKeyReleased(key -> {
            if (key.equals("back_space")) {
                controller.handleBackspace();
            } else if (key.equals("enter")) {
                controller.handleEnter();
            } else if (key.length() == 1) {
                controller.handleKeyInput(key.charAt(0));
            } else if (key.equals("escape")) {
                System.exit(0);
            }
        });
    }

    @Override
    protected Component[] createComponents(WordleModel model) {
        Component[] components = new Component[37];
        components[0] = new Background();
        components[1] = new Gridshadow();
        components[2] = new Grid5x6();
        components[3] = new Tastatur();

        int index = 4;
        int y = 27;
        for (int i = 0; i < 6; i++){
            int x = 475;
            for (int j = 0; j < 5; j++){
                // ColoredLabel statt Label verwenden!
                label[i][j] = new ColoredLabel(model.getLetter(i, j), x + 25, y, 50);
                label[i][j].setTextAlignCenter();
                components[index] = label[i][j];
                index++;
                x += 54;
            }
            y += 57;
        }

        messageLabel = new Label("", 600, 420, 50);
        messageLabel.setTextAlignCenter();
        messageLabel.setBold(true);
        components[34] = messageLabel;

        infoLabel = new Label("Für Restart 'R' drücken \nZum beenden 'Esc' drücken ",2,810,30);
        infoLabel.setTextAlignLeft();
        infoLabel.setItalic(true);
        infoLabel.setTextColor(new Color(255,255,0));
        components[35] = infoLabel;

        wrongInput = new Label("",600,420,50);
        wrongInput.setTextAlignCenter();
        wrongInput.setTextColor(new Color(255,255,0));
        components[36] = wrongInput;

        return components;
    }

    @Override
    public void updateComponents(WordleModel model) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                // 1. Text aktualisieren
                label[i][j].setText(model.getLetter(i, j));

                // 2. Farbe basierend auf Model-Daten setzen
                int colorValue = model.GetColor(i, j);

                if (colorValue == 1) {
                    // Grün - richtiger Buchstabe, richtige Position
                    label[i][j].setBackgroundColor(new Color(34, 139, 34));
                    label[i][j].setTextColor(new Color(255, 255, 255));

                } else if (colorValue == -1) {
                    // Orange - richtiger Buchstabe, falsche Position
                    label[i][j].setBackgroundColor(new Color(255, 165, 0));
                    label[i][j].setTextColor(new Color(255, 255, 255));

                } else if (colorValue == 0 && i < model.getCurrentRowIndex()) {
                    // Grau - Buchstabe nicht im Wort (nur wenn Buchstabe existiert)
                    label[i][j].setBackgroundColor(new Color(190, 190, 190));
                    label[i][j].setTextColor(new Color(0, 0, 0));

                } else {
                    // Weiß - noch nicht ausgefüllt oder leer
                    label[i][j].setBackgroundColor(new Color(225, 225, 225));
                    label[i][j].setTextColor(new Color(0, 0, 0));
                }
            }
        }

        if (model.isGameWon()) {
            messageLabel.setText("GEWONNEN!");
            messageLabel.setTextColor(new Color(0, 200, 0));
        } else if (model.isGameLost()) {
            messageLabel.setText("VERLOREN! Wort: " + model.getSolution().toUpperCase());
            messageLabel.setTextColor(new Color(200, 0, 0));
        } else {
            messageLabel.setText("");  // Leer während Spiel läuft
        }

        if (model.isWrongInput()){
            wrongInput.setText("Ungültiges Wort!");
        } else wrongInput.setText("");

    }
}
package wordle.view.gui;

import ch.mvcbase.ComponentGuiBase;
import ch.trick17.gui.Color;
import ch.trick17.gui.Gui;
import ch.trick17.gui.component.Component;
import ch.trick17.gui.widget.Button;
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
    ColoredKeyboard keyboard;
    Button help;
    Rules rules;

    /**
     * initialisiert das Gui
     * @param controller ruft den controller auf für die Logik
     * @param frameRate setzt die FrameRate des Gui fest
     */
    public WordleGUI(WordleController controller, int frameRate) {
        label = new ColoredLabel[6][5];
        super(controller, "Wordle", (int) (IMG_WIDTH * SCALE), (int) ((IMG_HEIGHT + 28) * SCALE), frameRate);
    }

    /**
     * Handelt die einzelnen eingaben der Tastatur
     * @param controller ruft die Methoden aus dem Controller auf um die Eingaben zu verarbeiten.
     */
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

    /**
     * Erstellt das Gui und kreiirt die einzelnen Componente.
     * @param model ruft die Klasse Model auf und benutzt die verschiedenen Methoden darin
     * @return Gibt die Components in das Gui zurück
     */
    @Override
    protected Component[] createComponents(WordleModel model) {
        Component[] components = new Component[38];
        components[0] = new Background();
        components[1] = new Grid5x6();

        keyboard = new ColoredKeyboard(model);
        components[2] = keyboard;

        int index = 3;
        int y = 154;
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

        messageLabel = new Label("", 600, 520, 50);
        messageLabel.setTextAlignCenter();
        messageLabel.setBold(true);
        components[33] = messageLabel;

        infoLabel = new Label("To Restart the Round Press 'R' \nTo end the Game press 'ESC' ",2,810,30);
        infoLabel.setTextAlignLeft();
        infoLabel.setItalic(true);
        infoLabel.setTextColor(new Color(255,255,255));
        components[34] = infoLabel;

        wrongInput = new Label("",600,520,50);
        wrongInput.setTextAlignCenter();
        wrongInput.setTextColor(new Color(0,0,0));
        components[35] = wrongInput;

        help = new Button("?",true,1135,10,55,55);
        components[36] = help;

        rules = new Rules();
        components[37] = rules;

        return components;
    }

    /**
     * Updated die bereits erstellten Komponenten im GUI.
     * @param model ruft die Klasse Model auf um die verschiedenen Methoden zu nutzen.
     */
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
            messageLabel.setText("CONGRATULATIONS YOU'VE WON!");
            messageLabel.setTextColor(new Color(34, 139, 34));
        } else if (model.isGameLost()) {
            messageLabel.setText("GAME OVER! \n correct Word: " + model.getSolution().toUpperCase());
            messageLabel.setTextColor(new Color(200, 0, 0));
        } else {
            messageLabel.setText("");  // Leer während Spiel läuft
        }
        if (model.isWrongInput()){
            wrongInput.setTextColor(new Color(255,0,0));
            wrongInput.setBold(true);
            wrongInput.setText("INVALID INPUT!");
        } else wrongInput.setText("");

        if (help.isHovered()){
            rules.setVisible(true);
        } else rules.setVisible(false);
    }
}
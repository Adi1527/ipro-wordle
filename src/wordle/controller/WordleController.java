package wordle.controller;

import ch.mvcbase.ControllerBase;

public class WordleController extends ControllerBase<WordleModel> {

    public WordleController(WordleModel model) {
        super(model);
    }

    public void handleKeyInput(char key) {
        // Blockiere Input wenn Spiel vorbei
        if (model.isGameWon() || model.isGameLost()) {
            char lowerKey = Character.toLowerCase(key);
            if (lowerKey == 'r'){
                model.Reset();
            }
            return;
        }
        char lowerKey = Character.toLowerCase(key);
        if (lowerKey >= 'a' && lowerKey <= 'z') {
            model.addLetter(lowerKey);
        }
    }

    public void handleBackspace() {
        // Blockiere Input wenn Spiel vorbei
        if (model.isGameWon() || model.isGameLost()) {
            return;
        }

        model.removeLetter();
    }

    public void handleEnter() {
        boolean wrongInput = false;
        // Blockiere Input wenn Spiel vorbei
        if (model.isGameWon() || model.isGameLost()) {
            return;
        }
        // Wort submitten (validierung passiert im Model)
        wrongInput = model.submitword();
        if (wrongInput == false){
            checkGameState();
        }
        // Nach submit: Spielstatus prüfen

    }

    private void checkGameState() {
        // Hole aktuelles Wort (das gerade submitted wurde ist in vorheriger Zeile)
        int lastRow = model.getCurrentRowIndex() - 1;  // -1 weil submit schon currentrow++ gemacht hat

        if (lastRow < 0) {
            return;  // Noch nichts submitted
        }

        String lastWord = "";
        for (int i = 0; i < 5; i++) {
            lastWord += model.getLetter(lastRow, i);
        }

        String solution = model.getSolution();

        // Gewonnen? (Vergleiche beide in lowercase)
        if (lastWord.toLowerCase().equals(solution.toLowerCase())) {
            model.setGameWon(true);
            return;
        }

        // Verloren? (6 Versuche = currentRow ist jetzt 6)
        if (model.getCurrentRowIndex() >= 6) {
            model.setGameLost(true);
        }
    }
}
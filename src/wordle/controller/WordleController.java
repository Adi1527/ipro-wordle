package wordle.controller;

import ch.mvcbase.ControllerBase;

public class WordleController extends ControllerBase<WordleModel> {

    public WordleController(WordleModel model) {
        super(model);
    }

    /**
     * reagiert auf eingaben der Tastatur, nur auf buchstaben, wenn das spiel fertig ist nimmt es nur 'r' an für den
     * reset ansonsten alle von a-z
     * @param key der char welcher entsteht beim keyrelease
     */
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

    /**
     * handlet den backspace um buchstaben zu entfernen
     */
    public void handleBackspace() {
        // Blockiere Input wenn Spiel vorbei
        if (model.isGameWon() || model.isGameLost()) {
            return;
        }
        model.removeLetter();
    }

    /**
     * handelt die eingabe des enter key.
     */
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

    /**
     * überprüft ob das spiel schon beendet ist und ob es gewonnen oder verloren wurde
     */
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
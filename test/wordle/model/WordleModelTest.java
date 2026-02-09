package wordle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wordle.controller.WordleController;
import wordle.controller.WordleModel;

import static org.junit.jupiter.api.Assertions.*;

class WordleModelTest {

    private WordleModel model;
    private WordleController controller;

    @BeforeEach
    void setUp() {
        model = new WordleModel();
        controller = new WordleController(model);
    }

    @Test
    void testRemoveLetterAtStart() {
        model.removeLetter();
        assertEquals(0, model.getCurrentCol());
    }

    @Test
    void testColorLogic() {
        // Wir nutzen die interne Hilfsklasse für den Test der Logik-Methode
        // Annahme: Lösung ist "ABBEY"
        String solution = "abbey";
        WordleModel.GuessString[] solutionArray = model.GuessToArray(solution);

        // Test-Guess: "BABES"
        // B -> Gelb (an falscher Stelle)
        // A -> Gelb (an falscher Stelle)
        // B -> Grün (an richtiger Stelle)
        // E -> Grün (an richtiger Stelle)
        // S -> Grau (nicht vorhanden)
        WordleModel.CustomString[] guess = model.TheGuessToArray("babes");

        WordleModel.CustomString[] result = model.CheckLetterAndColor(guess, solutionArray);

        assertEquals(-1, result[0].color, "Erstes B sollte gelb sein");
        assertEquals(-1, result[1].color, "A sollte gelb sein");
        assertEquals(1,  result[2].color, "Zweites B sollte grün sein");
        assertEquals(1,  result[3].color, "E sollte grün sein");
        assertEquals(0,  result[4].color, "S sollte grau sein");
    }

    @Test
    void testSubmitTooShortWord(){
        model.addLetter('t');
        model.addLetter('e');
        model.addLetter('s');
        model.addLetter('t');

        boolean wrongInput = model.submitword();

        assertTrue(wrongInput);
        assertTrue(model.isWrongInput());
        assertEquals(0, model.getCurrentRowIndex(), "Reihe darf nicht wechseln");
    }

    @Test
    void testKeyboardStatusUpdate() {
        WordleModel.CustomString[] guess = model.TheGuessToArray("about");
        guess[0].color = 1; // A ist grün

        model.addLetter('a');
        model.addLetter('b');
        model.addLetter('o');
        model.addLetter('u');
        model.addLetter('t');

        model.submitword();

        int statusA = model.getKeyStatus('a');
        assertNotEquals(0, statusA);
    }

    @Test
    void testDuplicateLetterLogic() {
        // Lösung: "PRESS" (ein E vorhanden)
        // Guess:  "GEESE" (drei E's vorhanden)
        WordleModel.GuessString[] solutionArray = model.GuessToArray("press");
        WordleModel.CustomString[] guess = model.TheGuessToArray("geese");

        WordleModel.CustomString[] result = model.CheckLetterAndColor(guess, solutionArray);

        // Das 'E' an Stelle 2 (Index 2) ist korrekt (Grün).
        // Das 'E' an Stelle 1 und 4 sollte grau sein, da das einzige E bereits verbraucht ist.
        assertEquals(1, result[2].color, "Das mittlere E muss grün sein");
        assertEquals(0, result[1].color, "Das erste E sollte grau sein (bereits durch das grüne E verbraucht)");
        assertEquals(0, result[4].color, "Das letzte E sollte grau sein");
    }

    @Test
    void testReset() {
        model.addLetter('w');
        model.setGameWon(true);
        model.Reset();

        assertEquals(0, model.getCurrentRowIndex());
        assertEquals("", model.getLetter(0, 0));
        assertFalse(model.isGameWon());
    }

}

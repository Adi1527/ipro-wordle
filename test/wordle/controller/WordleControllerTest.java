package wordle.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordleControllerTest {

    private WordleModel model;
    private WordleController controller;

    @BeforeEach
    void setUp() {
        model = new WordleModel();
        controller = new WordleController(model);
    }

    // ========== Tests für handleKeyInput ==========

    @Test
    void testHandleKeyInput_AddsBuchstabe() {
        controller.handleKeyInput('a');

        assertEquals("A", model.getLetter(0, 0));
        assertEquals(1, model.getCurrentCol());
    }

    @Test
    void testHandleKeyInput_ConvertsToLowercase() {
        controller.handleKeyInput('A');

        assertEquals("A", model.getLetter(0, 0));
    }

    @Test
    void testHandleKeyInput_IgnoresNonLetters() {
        controller.handleKeyInput('1');
        controller.handleKeyInput('!');
        controller.handleKeyInput(' ');

        assertEquals("", model.getLetter(0, 0));
        assertEquals(0, model.getCurrentCol());
    }

    @Test
    void testHandleKeyInput_BlocksWhenGameWon() {
        model.setGameWon(true);

        controller.handleKeyInput('a');

        assertEquals("", model.getLetter(0, 0));
    }

    @Test
    void testHandleKeyInput_ResetsOnR_WhenGameWon() {
        // Spiel vorbereiten - gewinnen
        model.setGameWon(true);
        String originalSolution = model.getSolution();

        controller.handleKeyInput('r');

        assertFalse(model.isGameWon());
        assertEquals(0, model.getCurrentRowIndex());
        // Neue Lösung könnte gleich oder unterschiedlich sein
    }

    // ========== Tests für handleBackspace ==========

    @Test
    void testHandleBackspace_RemovesLetter() {
        // Erst Buchstaben hinzufügen
        controller.handleKeyInput('a');
        controller.handleKeyInput('b');
        assertEquals(2, model.getCurrentCol());

        // Dann entfernen
        controller.handleBackspace();

        assertEquals(1, model.getCurrentCol());
        assertEquals("", model.getLetter(0, 1));
    }

    @Test
    void testHandleBackspace_BlocksWhenGameWon() {
        controller.handleKeyInput('a');
        model.setGameWon(true);

        controller.handleBackspace();

        // Buchstabe sollte noch da sein
        assertEquals("A", model.getLetter(0, 0));
    }

    // ========== Tests für handleEnter ==========

    @Test
    void testHandleEnter_SubmitsValidWord() {
        // Ein gültiges Wort eingeben (muss in der Wortliste sein!)
        // Annahme: "about" ist in der Liste
        char[] letters = {'a', 'b', 'o', 'u', 't'};
        for (char letter : letters) {
            controller.handleKeyInput(letter);
        }

        controller.handleEnter();

        // Sollte zur nächsten Zeile gehen
        assertEquals(1, model.getCurrentRowIndex());
        assertEquals(0, model.getCurrentCol());
    }

    @Test
    void testHandleEnter_RejectsInvalidWord() {
        // Ein ungültiges Wort eingeben
        char[] letters = {'x', 'x', 'x', 'x', 'x'};
        for (char letter : letters) {
            controller.handleKeyInput(letter);
        }

        controller.handleEnter();

        // Sollte NICHT zur nächsten Zeile gehen
        assertEquals(0, model.getCurrentRowIndex());
        assertTrue(model.isWrongInput());
    }

    @Test
    void testHandleEnter_BlocksWhenGameWon() {
        model.setGameWon(true);

        controller.handleEnter();

        // Nichts sollte passieren
        assertEquals(0, model.getCurrentRowIndex());
    }

    // ========== Tests für checkGameState ==========

    @Test
    void testCheckGameState_WinsWhenCorrectWord() {
        // Lösung herausfinden
        String solution = model.getSolution();

        // Lösung eingeben
        for (char letter : solution.toCharArray()) {
            controller.handleKeyInput(letter);
        }

        controller.handleEnter();

        assertTrue(model.isGameWon());
    }

    @Test
    void testCheckGameState_LosesAfter6Attempts() {
        // 6x ein falsches Wort eingeben
        for (int i = 0; i < 6; i++) {
            // Ein gültiges aber falsches Wort (nicht die Lösung)
            for (char letter : "about".toCharArray()) {
                controller.handleKeyInput(letter);
            }
            controller.handleEnter();
        }

        assertTrue(model.isGameLost());
    }

    @Test
    void testCheckGameState_ContinuesWhenStillPlaying() {
        // Ein falsches Wort eingeben
        for (char letter : "about".toCharArray()) {
            controller.handleKeyInput(letter);
        }

        controller.handleEnter();

        assertFalse(model.isGameWon());
        assertFalse(model.isGameLost());
        assertEquals(1, model.getCurrentRowIndex());
    }

    // ========== Integrations-Test ==========

    @Test
    void testCompleteGameFlow() {
        // Vollständiger Spielablauf
        String solution = model.getSolution();

        // Erste 2 Versuche: Falsche Wörter
        for (int i = 0; i < 2; i++) {
            for (char letter : "about".toCharArray()) {
                controller.handleKeyInput(letter);
            }
            controller.handleEnter();
        }

        assertFalse(model.isGameWon());
        assertEquals(2, model.getCurrentRowIndex());

        // Dritter Versuch: Richtiges Wort
        for (char letter : solution.toCharArray()) {
            controller.handleKeyInput(letter);
        }
        controller.handleEnter();

        assertTrue(model.isGameWon());

        // Nach Gewinn: Input blockiert
        controller.handleKeyInput('a');
        assertEquals("", model.getLetter(3, 0));

        // Reset mit 'r'
        controller.handleKeyInput('r');
        assertFalse(model.isGameWon());
        assertEquals(0, model.getCurrentRowIndex());
    }
}
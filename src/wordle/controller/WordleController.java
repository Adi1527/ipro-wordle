package wordle.controller;

import ch.mvcbase.ControllerBase;


public class WordleController extends ControllerBase<WordleModel> {
    /**
     * The Controller needs a Model.
     *
     * @param model Model managed by this Controller
     */
    public WordleController(WordleModel model) {
        super(model);
    }


    public void handleKeyInput(char key) {
        char lowerKey = Character.toLowerCase(key);
        if (lowerKey >= 'a' && lowerKey <= 'z') {
            model.addLetter(lowerKey);
        }
    }


    public void handleBackspace() {
        model.removeLetter();
    }

    public void handleEnter() {
        model.submitword();
    }
}


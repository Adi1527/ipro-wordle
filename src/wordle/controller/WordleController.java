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
}

package ch.mvcbase;


/**
 * Base class for graphical user interfaces (GUIs) dedicated to games. This class extends
 * {@code GuiBase} and provides functionality tailored explicitly for game GUIs, ensuring seamless
 * integration with a game controller and model.
 *
 * @param <M> The type of the model representing the game's current state.
 * @param <C> The type of the game controller responsible for game logic, which must be a subclass of
 *            {@code GameControllerBase}.
 */
public abstract class GameGuiBase <M, C extends GameControllerBase<M>>  extends GuiBase<M, C> {

    public GameGuiBase(C controller, String title, int width, int height, int frameRate) {
        super(controller, title, width, height, frameRate);
    }

    @Override
    public final void initializeComponents(M model) {
        // there are no components in a pure game
    }

    @Override
    protected final void performNextStep(C controller) {
        controller.step();
        super.performNextStep(controller);
    }
}

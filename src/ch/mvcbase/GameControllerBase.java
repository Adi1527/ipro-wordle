package ch.mvcbase;

/**
 * Represents an abstract base class for all game controllers. This class extends {@link ControllerBase}
 * and provides additional functionality to handle game-specific actions such as game loop steps and
 * termination events.
 *
 * @param <M> The type of the model managed by this controller.
 */
public abstract class GameControllerBase<M> extends ControllerBase<M> {

    private Runnable terminationListener;

    protected GameControllerBase(M model) {
        super(model);
        onStartUp();
    }

    /**
     * Defines a single "step" in the game loop. This method represents the core logic executed
     * during each iteration of the game loop and is meant to be implemented by subclasses.
     * <p>
     * The implementation of this method should contain the specific behavior and updates
     * required for each step, typically involving game state updates, interactions, or computations.
     * <p>
     * This method is abstract and must be overridden by any concrete subclass.
     */
    public abstract void step();

    public void setTerminationListener(Runnable terminationListener){
        this.terminationListener = terminationListener;
    }

    public void terminateGame() {
        if(terminationListener != null){
            terminationListener.run();
        }
    }
}

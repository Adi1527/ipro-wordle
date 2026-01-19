package ch.mvcbase;

import java.util.Objects;
import java.util.function.Consumer;

import ch.trick17.gui.impl.swing.Window;

/**
 * An abstract base class for graphical user interfaces (GUIs), providing a structure
 * to integrate with a controller and model using the {@code Projector} interface.
 * This class manages the lifecycle of the GUI, including initialization, rendering,
 * and handling user inputs like key presses and releases.
 *
 * @param <M> The type of the model associated with the GUI.
 * @param <C> The type of the controller responsible for manipulating the model, extending {@code ControllerBase<M>}.
 */
public abstract class GuiBase<M, C extends ControllerBase<M>>  extends Window implements Projector<M, C> {

    private final C controller;
    private final int frameRate;

    private Consumer<String> keyReleasedHandler = key -> {};
    private Consumer<String> keyPressedHandler  = key -> {};

    public GuiBase(C controller, String title, int width, int height, int frameRate) {
        super(title, width, height);
        Objects.requireNonNull(controller);

        this.controller = controller;
        this.frameRate = frameRate;
        init(controller);
    }

    public void proceedUntilClosed(){
        open();
        runUntilClosed(1000/frameRate);
    }

    public void shutdown(){
        close();
    }

    protected final void setOnKeyReleased(Consumer<String> keyHandler){
        Objects.requireNonNull(keyHandler);
        keyReleasedHandler = keyHandler;
    }

    protected final void setWhileKeyPressed(Consumer<String> keyHandler){
        Objects.requireNonNull(keyHandler);
        keyPressedHandler = keyHandler;
    }

    @Override
    protected final void repaint(boolean clear) {
        if (!getTypedKeys().isEmpty()) {
            for (String key : getTypedKeys()) {
                keyReleasedHandler.accept(key);
            }
        }
        if (!getPressedKeys().isEmpty()) {
            for (String key : getPressedKeys()) {
                keyPressedHandler.accept(key);
            }
        }
        performNextStep(controller);
        super.repaint(clear);
        redraw(controller.model);
    }

    protected abstract void redraw(M model);

    protected void performNextStep(C controller){
        updateComponents(controller.model);
    }
}

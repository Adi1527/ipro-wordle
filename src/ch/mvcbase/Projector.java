package ch.mvcbase;

import java.util.Objects;

/**
 * Projector is the common interface for both, GUI and PUI.
 */
public interface Projector<M, C extends ControllerBase<M>> {

    /**
     * needs to be called inside the constructor of your UI-part
     */
	default void init(C controller) {
        Objects.requireNonNull(controller);
        initializeSelf();
        initializeComponents(controller.getModel());
		setupEventHandler(controller);
		updateComponents(controller.getModel());
	}

    /**
     * Everything that needs to be done to initialize the UI-part itself.
     * <p>
     * For GUIs loading stylesheet-files or additional fonts are typical examples.
     */
    default void initializeSelf(){
    }

    /**
     * completely initialize all necessary UI-elements (like buttons, text-fields, etc. on GUI or distance sensors on PUI)
     */
    void initializeComponents(M model);


    /**
     * Triggering some action on the Controller if the user interacts with the UI.
     * <p>
     * There's no need to have access to Model for this task.
     * <p>
     * All EventHandlers will call a single method on the Controller.
     * <p>
     * If you are about to call more than one method, you should introduce a new method on Controller.
     */
	default void setupEventHandler(C controller) {
	}

    /**
     * Before the UI is redrawn, the whole model state has to be synchronized with the UI state.
     * <p>
     * There's no need to have access to controller for this task.
     * <p>
     * Update all your UI elements according to the new model state.
     */
	default void updateComponents(M model) {
	}
}

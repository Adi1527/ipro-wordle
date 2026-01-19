package ch.mvcbase;

import ch.trick17.gui.component.Component;

/**
 * A GUI completely build with Trick17-components, like `ch.trick17.gui.widget.*` or
 * custom-made components that implement `ch.trick17.gui.component.Drawable`
 *
 * @param <M> the model class
 * @param <C> the controller class
 */
public abstract class ComponentGuiBase<M, C extends ControllerBase<M>>  extends GuiBase<M, C> {

    public ComponentGuiBase(C controller, String title, int width, int height, int frameRate) {
        super(controller, title, width, height, frameRate);
    }

    @Override
    public final void initializeComponents(M model) {
        var components = createComponents(model);
        for (var component : components) {
            addComponent(component);
        }
    }

    protected abstract Component[] createComponents(M model);

    @Override
    protected final void redraw(M model) {
        // don't allow pure component-based GUIs to redraw
    }
}

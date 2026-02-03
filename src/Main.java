import wordle.controller.WordleController;
import wordle.controller.WordleModel;
import wordle.view.gui.WordleGUI;

void main() {
    WordleModel model = new WordleModel();
    WordleController controller = new WordleController(model);
    WordleGUI gui = new WordleGUI(controller, 30);
    gui.proceedUntilClosed();
}
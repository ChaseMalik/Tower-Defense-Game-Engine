package gameAuthoring.scenes;

import gameAuthoring.control.AuthorControl;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class TowerBuilder extends Scene {

    public TowerBuilder (BorderPane root) {
        super(root, AuthorControl.SCREEN_WIDTH, AuthorControl.SCREEN_HEIGHT);
    }

}

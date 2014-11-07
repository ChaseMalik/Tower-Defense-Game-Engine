package gameAuthoring.scenes;

import gameAuthoring.control.AuthorControl;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class PathBuilder extends Scene {

    public PathBuilder (BorderPane root) {
        super(root, AuthorControl.SCREEN_WIDTH, AuthorControl.SCREEN_WIDTH);
    }

}

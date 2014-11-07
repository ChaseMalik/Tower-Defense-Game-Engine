package gameAuthoring.scenes;

import gameAuthoring.control.AuthorControl;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class EnemyBuilder extends Scene {

    public EnemyBuilder (BorderPane root) {
        super(root, AuthorControl.SCREEN_WIDTH, AuthorControl.SCREEN_HEIGHT);
    }

}

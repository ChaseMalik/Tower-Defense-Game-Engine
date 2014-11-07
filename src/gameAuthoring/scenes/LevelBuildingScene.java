package gameAuthoring.scenes;

import gameAuthoring.levelData.LevelDataHolder;
import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class LevelBuildingScene extends Scene {

    public LevelBuildingScene (BorderPane root, LevelDataHolder holder) {
        super(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
    }

}

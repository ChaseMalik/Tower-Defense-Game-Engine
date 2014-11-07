package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.pathData.PathDataHolder;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class PathBuildingScene extends Scene {
    

    public PathBuildingScene (BorderPane root, PathDataHolder holder) {
        super(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_WIDTH);
    }

}

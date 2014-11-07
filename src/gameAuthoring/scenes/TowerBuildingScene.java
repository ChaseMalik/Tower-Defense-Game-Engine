package gameAuthoring.scenes;

import gameAuthoring.actorData.actorDataHolders.TowerDataHolder;
import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class TowerBuildingScene extends Scene {

    public TowerBuildingScene (BorderPane root, TowerDataHolder holder) {
        super(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
    }

}

package gameAuthoring.scenes;

import gameAuthoring.actorData.actorDataHolders.EnemyDataHolder;
import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class EnemyBuildingScene extends Scene {

    public EnemyBuildingScene (BorderPane root, EnemyDataHolder data) {
        super(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
    }

}

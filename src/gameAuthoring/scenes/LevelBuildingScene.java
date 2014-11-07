package gameAuthoring.scenes;

import gameAuthoring.levelData.LevelDataHolder;
import javafx.scene.layout.BorderPane;

public class LevelBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Level Building";
    
    private LevelDataHolder myLevelDataHolder;

    public LevelBuildingScene (BorderPane root) {
        super(root, TITLE);
        myLevelDataHolder = new LevelDataHolder();
    }

}

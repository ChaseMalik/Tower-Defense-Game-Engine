package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.scenes.BuildingScene;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.scene.layout.BorderPane;

public class TowerBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Tower Building";
    
    private List<BaseActor> myCreatedEnemies;
    
    public TowerBuildingScene (BorderPane root, List<BaseActor> enemies) {
        super(root, TITLE);
        myCreatedEnemies = enemies;
    }
}
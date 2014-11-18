package gameAuthoring.scenes;

import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.scene.layout.BorderPane;

public class TowerBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Tower Building";
    private List<BaseActor> myEnemies;
    

    public TowerBuildingScene (BorderPane root, List<BaseActor> enemies) {
        super(root, TITLE);
        myEnemies = enemies;
    }

}

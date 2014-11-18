package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.scene.layout.BorderPane;

public class TowerBuildingScene extends ActorBuildingScene {
    
    private static final String TITLE = "Tower";
    private static final String IMG_DIR = "./src/gameAuthoring/Resources/towerImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/TowerBehaviors.xml";

    private List<BaseActor> myCreatedEnemies;
    
    public TowerBuildingScene (BorderPane root, List<BaseActor> enemies, 
                               List<BackendRoute> enemyRoutes) {
        super(root, enemyRoutes, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
        myCreatedEnemies = enemies;
    }
}
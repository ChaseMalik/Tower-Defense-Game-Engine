package gameAuthoring.scenes.actorBuildingScenes;


import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import java.util.List;
import javafx.scene.layout.BorderPane;

public class EnemyBuildingScene extends ActorBuildingScene {


    private static final String TITLE = "Enemy Building";

    public EnemyBuildingScene (BorderPane root, List<BackendRoute> enemyRoutes) {
        super(root, TITLE);
    }
}
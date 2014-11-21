package gameAuthoring.scenes.actorBuildingScenes;


import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.BorderPane;

public class EnemyBuildingScene extends ActorBuildingScene {

    private static final String TITLE = "Enemy";
    private static final String IMG_DIR = "./src/gameAuthoring/Resources/enemyImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/EnemyBehaviors.xml";

    public EnemyBuildingScene (BorderPane root, List<BackendRoute> enemyRoutes) {
        super(root, enemyRoutes, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
    }

    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        myActors.add(new BaseActor(iBehaviorMap,
                                   myActorImage,
                                   myActorNameField.getText()));       
    }
}
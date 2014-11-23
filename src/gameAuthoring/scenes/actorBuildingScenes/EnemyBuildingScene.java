package gameAuthoring.scenes.actorBuildingScenes;


import gameAuthoring.scenes.actorBuildingScenes.actorListView.CreatedEnemiesDisplay;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;

/**
 * Scene to build a new enemy.
 * @author Austin Kyker
 *
 */
public class EnemyBuildingScene extends ActorBuildingScene {

    private static final String TITLE = "Enemy";
    private static final String IMG_DIR = "./src/gameAuthoring/Resources/enemyImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/actorBehaviors/EnemyBehaviors.xml";

    private List<BaseEnemy> myEnemies;

    public EnemyBuildingScene (BorderPane root, List<BackendRoute> enemyRoutes) {
        super(root, enemyRoutes, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
    }

    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        //TODO
        myEnemies.add(new BaseEnemy(iBehaviorMap,
                                    myActorImgPath,
                                    myActorNameField.getText(), 
                                    5,
                                    null));       
    }

    @Override
    protected void initializeActorsAndBuildActorDisplay () {
        myEnemies = FXCollections.observableArrayList();
        CreatedEnemiesDisplay enemyDisplay = new CreatedEnemiesDisplay(myEnemies);
        myPane.setLeft(enemyDisplay);        
    }
    
    @Override
    protected void finishBuildingActors() {        
        this.setChanged();
        this.notifyObservers(myEnemies);
    }
}
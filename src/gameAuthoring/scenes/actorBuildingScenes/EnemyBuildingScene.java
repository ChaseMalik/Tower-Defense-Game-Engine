package gameAuthoring.scenes.actorBuildingScenes;


import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.controllerInterfaces.EnemyConfiguring;
import gameAuthoring.scenes.actorBuildingScenes.actorListView.CreatedEnemiesDisplay;
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
    private static final String IMG_DIR = AuthorController.gameDir + "/enemyImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/actorBehaviors/EnemyBehaviors.xml";

    private List<BaseEnemy> myEnemies;
    private EnemyConfiguring myEnemyConfiguringController;

    public EnemyBuildingScene (BorderPane root, EnemyConfiguring controller) {
        super(root, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
        myEnemyConfiguringController = controller;
        
    }

    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        //TODO
        myEnemies.add(new BaseEnemy(iBehaviorMap,
                                    myActorImgPath,
                                    myActorNameField.getText(), 
                                    myRangeSliderContainer.getSliderValue(),
                                    myEnemyConfiguringController.fetchEnemyRoutes()));       
    }

    @Override
    protected void initializeActorsAndBuildActorDisplay () {
        myEnemies = FXCollections.observableArrayList();
        CreatedEnemiesDisplay enemyDisplay = new CreatedEnemiesDisplay(myEnemies);
        myPane.setLeft(enemyDisplay);        
    }
    
    @Override
    protected void finishBuildingActors() {
        myEnemyConfiguringController.configureEnemies(myEnemies);
    }

    @Override
    protected void configureAndDisplayRightPane () {}
}
package gameAuthoring.mainclasses;

import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.GSONWritingScene;
import gameAuthoring.scenes.WelcomeScene;
import gameAuthoring.scenes.actorBuildingScenes.EnemyBuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.TowerBuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameAuthoring.scenes.levelBuilding.LevelBuildingScene;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoutesGenerator;
import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.GSON.GSONFileReader;
import utilities.GSON.GSONFileWriter;
import utilities.errorPopup.ErrorPopup;

/**
 * The purpose of this class is to manage the different scenes (path building and author
 * creation). The class also will hold the enemies, towers, and level objects which
 * it will write to JSON files at the end of the authoring process.
 * @author Austin Kyker
 *
 */
public class AuthorController extends Application implements Observer {

    private static final String NOT_ENOUGH_ENEMIES_MSG = "You need at least one type of enemy";
    private static final String NOT_ENOUGH_TOWERS_MSG = "You need at least one type of tower";
    public static final double SCREEN_WIDTH = 1000;
    public static final double SCREEN_HEIGHT = 620;
    private static final GSONFileWriter GSON_WRITER = new GSONFileWriter();
    public static String gameDir;
    
    private String gameName;

    private EnemyBuildingScene myEnemyBuildingScene;
    private TowerBuildingScene myTowerBuildingScene;
    private PathBuildingScene myPathBuildingScene;
    private LevelBuildingScene myLevelBuildingScene;
    private GSONWritingScene myGSONWritingScene;
    private WelcomeScene myWelcomeScene;

    private List<BackendRoute> myBackendRoutes;
    private List<BaseEnemy> myEnemies;
    private List<TowerUpgradeGroup> myTowerGroups;
    private List<BaseLevel> myLevels;

    private Stage myStage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        buildScenes();
//          showPathBuildingScene();
                showWelcomeScene();
//        List<BackendRoute> routes = new ArrayList<BackendRoute>();
//        routes.add(new BackendRoute());
//        myBackendRoutes = routes;
//        showEnemyBuildingScene();
//        showGSONWritingScene();
        configureAndDisplayStage();

    }

    private void configureAndDisplayStage () {
        myStage.setResizable(false);
        myStage.show(); 
    }

    private void buildScenes () {
        myPathBuildingScene = new PathBuildingScene(new BorderPane());
        myPathBuildingScene.addObserver(this);
    }


    public void showPathBuildingScene() {
        setSceneAndTitle(myPathBuildingScene);
    }

    public void showEnemyBuildingScene() {
        myEnemyBuildingScene = new EnemyBuildingScene(new BorderPane(), myBackendRoutes);
        setSceneAndTitle(myEnemyBuildingScene);
    }

    public void showTowerBuildingScene() {
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane(), myEnemies);
        setSceneAndTitle(myTowerBuildingScene);
    }

    public void showLevelBuildingScene() {
        myLevelBuildingScene = new LevelBuildingScene(new BorderPane(), myEnemies);
        setSceneAndTitle(myLevelBuildingScene);
    }

    private void setSceneAndTitle(BuildingScene scene) {
        scene.addObserver(this);
        myStage.setScene(scene.getScene());
        myStage.setTitle(scene.getTitle().concat(" Building"));
    }

    private void showWelcomeScene(){
        myWelcomeScene = new WelcomeScene();
        myWelcomeScene.addObserver(this);
        myStage.setScene(myWelcomeScene.getScene());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update (Observable ob, Object value) {
        if(ob.equals(myWelcomeScene)) {
            gameName = (String) value;
            gameDir = "./Games/" + gameName + "/";
            File dir = new File(gameDir);
            
            dir.mkdir();
            showPathBuildingScene();
        }
        else if(ob.equals(myPathBuildingScene)){
            myBackendRoutes = BackendRoutesGenerator.getBackendRoutes((Path) value);
            showEnemyBuildingScene();
        }
        else if(ob.equals(myEnemyBuildingScene)) {
            myEnemies = (List<BaseEnemy>) value;
            if(notEnoughEnemies()) {
                new ErrorPopup(NOT_ENOUGH_ENEMIES_MSG);
            }
            else {
                showTowerBuildingScene();
            }
        }
        else if(ob.equals(myTowerBuildingScene)) {
            myTowerGroups = (List<TowerUpgradeGroup>) value;
            if(notEnoughTowers()) {
                new ErrorPopup(NOT_ENOUGH_TOWERS_MSG);
            }
            else {
                showLevelBuildingScene();
            }
        }  
        else if(ob.equals(myLevelBuildingScene)) {
            myLevels = (List<BaseLevel>) value;
            showGSONWritingScene();
        }
    }

    private void showGSONWritingScene () {
        myGSONWritingScene = new GSONWritingScene(new BorderPane());
        myStage.setScene(myGSONWritingScene);
        myStage.setTitle("Writing Game"); 
        GSON_WRITER.writeGameFile(myTowerGroups, myLevels, gameDir); 
        GSONFileReader reader = new GSONFileReader();
        List<BaseLevel> list = reader.readLevelfromFile("levels", gameDir);
    }

    private boolean notEnoughTowers () {
        return myTowerGroups.size() < 1;
    }

    private boolean notEnoughEnemies () {
        return myEnemies.size() < 1;
    }
}

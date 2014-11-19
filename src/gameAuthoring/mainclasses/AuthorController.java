package gameAuthoring.mainclasses;

import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.EnemyBuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.TowerBuildingScene;
import gameAuthoring.scenes.levelBuilding.LevelBuildingScene;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoutesGenerator;
import gameEngine.actors.BaseActor;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AuthorController extends Application implements Observer {

    public static final double SCREEN_WIDTH = 1000;
    public static final double SCREEN_HEIGHT = 600;

    private EnemyBuildingScene myEnemyBuildingScene;
    private TowerBuildingScene myTowerBuildingScene;
    private PathBuildingScene myPathBuildingScene;
    private LevelBuildingScene myLevelBuildingScene;
    private List<BackendRoute> myBackendRoutes;
    private List<BaseActor> myEnemies;
    private List<BaseActor> myTowers;

    private Stage myStage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        buildScenes();
//        showPathBuildingScene();
        List<BackendRoute> routes = new ArrayList<BackendRoute>();
        routes.add(new BackendRoute());
        myBackendRoutes = routes;
        showEnemyBuildingScene();
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
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane(), myEnemies, myBackendRoutes);
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

    @SuppressWarnings("unchecked")
    @Override
    public void update (Observable ob, Object value) {
        if(ob.equals(myPathBuildingScene)){
            myBackendRoutes = BackendRoutesGenerator.getBackendRoutes((Path) value);
            showEnemyBuildingScene();
        }
        else if(ob.equals(myEnemyBuildingScene)) {
            myEnemies = (List<BaseActor>) value;
            showTowerBuildingScene();
        }
        else if(ob.equals(myTowerBuildingScene)) {
            myTowers = (List<BaseActor>) value;
            showLevelBuildingScene();
        }       
    }
}

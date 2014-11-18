package gameAuthoring.mainclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.LevelBuildingScene;
import gameAuthoring.scenes.TowerBuildingScene;
import gameAuthoring.scenes.enemyBuilding.EnemyBuildingScene;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoutesGenerator;
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
    private List<BackendRoute> myBackendRoutesRepresentation;

    private Stage myStage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        buildScenes();
//        showPathBuildingScene();
        List<BackendRoute> routes = new ArrayList<BackendRoute>();
        routes.add(new BackendRoute());
        myBackendRoutesRepresentation = routes;
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
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane());
        myLevelBuildingScene = new LevelBuildingScene(new BorderPane());
    }

    public void showPathBuildingScene() {
        setSceneAndTitle(myPathBuildingScene);
    }

    public void showEnemyBuildingScene() {
        myEnemyBuildingScene = new EnemyBuildingScene(new BorderPane(), myBackendRoutesRepresentation);
        setSceneAndTitle(myEnemyBuildingScene);
    }

    public void showTowerBuildingScene() {
        setSceneAndTitle(myTowerBuildingScene);
    }

    public void showLevelBuildingScene() {
        setSceneAndTitle(myLevelBuildingScene);
    }

    private void setSceneAndTitle(BuildingScene scene) {
        myStage.setScene(scene.getScene());
        myStage.setTitle(scene.getTitle());
    }

    @Override
    public void update (Observable ob, Object value) {
        if(ob.equals(myPathBuildingScene)){
            myBackendRoutesRepresentation = BackendRoutesGenerator.getBackendRoutes((Path) value);
            showEnemyBuildingScene();
        }      
    }
}

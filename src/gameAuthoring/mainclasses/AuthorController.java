package gameAuthoring.mainclasses;

import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.EnemyBuildingScene;
import gameAuthoring.scenes.LevelBuildingScene;
import gameAuthoring.scenes.TowerBuildingScene;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AuthorController extends Application {

    public static final double SCREEN_WIDTH = 700;
    public static final double SCREEN_HEIGHT = 600;
    
    private EnemyBuildingScene myEnemyBuildingScene;
    private TowerBuildingScene myTowerBuildingScene;
    private PathBuildingScene myPathBuildingScene;
    private LevelBuildingScene myLevelBuildingScene;
    
    private Stage myStage;
    
    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        buildScenes();
       // showPathBuildingScene();
        showEnemyBuildingScene();
        configureAndDisplayStage();
    }

    private void configureAndDisplayStage () {
        myStage.setResizable(false);
        myStage.show(); 
    }

    private void buildScenes () {
        myPathBuildingScene = new PathBuildingScene(new BorderPane());
        myEnemyBuildingScene = new EnemyBuildingScene(new BorderPane());
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane());
        myLevelBuildingScene = new LevelBuildingScene(new BorderPane());
    }
    
    public void showPathBuildingScene() {
        setSceneAndTitle(myPathBuildingScene);
    }
    
    public void showEnemyBuildingScene() {
        setSceneAndTitle(myEnemyBuildingScene);
    }
    
    public void showTowerBuildingScene() {
        setSceneAndTitle(myTowerBuildingScene);
    }
    
    public void showLevelBuildingScene() {
        setSceneAndTitle(myLevelBuildingScene);
    }
    
    private void setSceneAndTitle(BuildingScene scene) {
        myStage.setScene(scene);
        myStage.setTitle(scene.getTitle());
    }
}

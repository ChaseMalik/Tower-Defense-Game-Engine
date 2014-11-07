package gameAuthoring.mainclasses;

import gameAuthoring.actorData.actorDataHolders.EnemyDataHolder;
import gameAuthoring.actorData.actorDataHolders.TowerDataHolder;
import gameAuthoring.levelData.LevelDataHolder;
import gameAuthoring.pathData.PathDataHolder;
import gameAuthoring.scenes.EnemyBuildingScene;
import gameAuthoring.scenes.LevelBuildingScene;
import gameAuthoring.scenes.PathBuildingScene;
import gameAuthoring.scenes.TowerBuildingScene;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AuthorController extends Application {

    public static final double SCREEN_WIDTH = 700;
    public static final double SCREEN_HEIGHT = 600;
    
    private PathDataHolder  myPathDataHolder = new PathDataHolder();
    private EnemyDataHolder myEnemyDataHolder = new EnemyDataHolder();
    private TowerDataHolder myTowerDataHolder = new TowerDataHolder();
    private LevelDataHolder myLevelDataHolder = new LevelDataHolder();
    
    private EnemyBuildingScene myEnemyBuildingScene;
    private TowerBuildingScene myTowerBuildingScene;
    private PathBuildingScene myPathBuildingScene;
    private LevelBuildingScene myLevelBuildingScene;
    
    private Stage myStage;
    
    public static void main(String[] args) { launch(args); }

    @Override
    public void start (Stage stage) throws Exception {
        myStage = stage;
        StageInitializer.setupStage(myStage);
        buildScenes();
    }

    private void buildScenes () {
        myPathBuildingScene = new PathBuildingScene(new BorderPane(), myPathDataHolder);
        myEnemyBuildingScene = new EnemyBuildingScene(new BorderPane(), myEnemyDataHolder);
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane(), myTowerDataHolder);
        myLevelBuildingScene = new LevelBuildingScene(new BorderPane(), myLevelDataHolder);
    }
    
    
    public void buildTowersJSONFile() {
        
    }
    
    public void buildLevelsJSONFile() {
        
    }
    
    public void buildEnemiesJSONFile() {
        
    }
    
    public void buildPathJSONFile() {
        
    }
}

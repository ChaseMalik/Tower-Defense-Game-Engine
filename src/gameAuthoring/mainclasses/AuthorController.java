package gameAuthoring.mainclasses;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import gameAuthoring.mainclasses.controllerInterfaces.EnemyConfiguring;
import gameAuthoring.mainclasses.controllerInterfaces.GeneralSettingsConfiguring;
import gameAuthoring.mainclasses.controllerInterfaces.LevelConfiguring;
import gameAuthoring.mainclasses.controllerInterfaces.PathConfiguring;
import gameAuthoring.mainclasses.controllerInterfaces.TowerConfiguring;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.GSONWritingScene;
import gameAuthoring.scenes.GeneralSettingScene;
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
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.GSON.GSONFileWriter;
import utilities.GSON.objectWrappers.GeneralSettingsWrapper;
import utilities.errorPopup.ErrorPopup;
import utilities.multilanguage.MultiLanguageUtility;


/**
 * The purpose of this class is to manage the different scenes (path building
 * and author creation). The class also will hold the enemies, towers, and level
 * objects which it will write to JSON files at the end of the authoring
 * process.
 * 
 * @author Austin Kyker
 *
 */
public class AuthorController extends Application implements
        GeneralSettingsConfiguring, PathConfiguring, TowerConfiguring,
        EnemyConfiguring, LevelConfiguring, Observer {

    private static final String SPANISH_PROPERTIES =
            "gameAuthoring.Resources.propertyFiles.English.properties";
    private static final String ENGLISH_PROPERTIES =
            "gameAuthoring.Resources.propertyFiles.Spanish.properties";
    public static final double SCREEN_WIDTH = 1100;
    public static final double SCREEN_HEIGHT = 633;
    private static final GSONFileWriter GSON_WRITER = new GSONFileWriter();
    public static String gameDir;

    private EnemyBuildingScene myEnemyBuildingScene;
    private TowerBuildingScene myTowerBuildingScene;
    private PathBuildingScene myPathBuildingScene;
    private LevelBuildingScene myLevelBuildingScene;
    private GSONWritingScene myGSONWritingScene;
    private WelcomeScene myWelcomeScene;
    private GeneralSettingScene myGeneralSettingScene;

    private List<BackendRoute> myBackendRoutes;
    private List<BaseEnemy> myEnemies;
    private List<TowerUpgradeGroup> myTowerGroups;
    private List<BaseLevel> myLevels;

    private Stage myStage;
    private String myBackgroundImageFileName;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.initLanguages(ENGLISH_PROPERTIES, SPANISH_PROPERTIES);
        myStage = stage;
        showWelcomeScene();
        configureAndDisplayStage();
    }

    private void configureAndDisplayStage () {
        myStage.show();
    }

    public void showPathBuildingScene () {
        myPathBuildingScene = new PathBuildingScene(new BorderPane(),
                                                    (PathConfiguring) this);
        setSceneAndTitle(myPathBuildingScene);
    }

    public void showEnemyBuildingScene () {
        myEnemyBuildingScene = new EnemyBuildingScene(new BorderPane(),
                                                      (EnemyConfiguring) this);
        setSceneAndTitle(myEnemyBuildingScene);
    }

    public void showTowerBuildingScene () {
        myTowerBuildingScene = new TowerBuildingScene(new BorderPane(),
                                                      (TowerConfiguring) this);
        setSceneAndTitle(myTowerBuildingScene);
    }

    public void showLevelBuildingScene () {
        myLevelBuildingScene = new LevelBuildingScene(new BorderPane(),
                                                      (LevelConfiguring) this);
        setSceneAndTitle(myLevelBuildingScene);
    }

    private void setSceneAndTitle (BuildingScene scene) {
        myStage.setScene(scene.getScene());
        myStage.setTitle(scene.getTitle().concat(" Building"));
    }

    private void showWelcomeScene () {
        myWelcomeScene = new WelcomeScene();
        myWelcomeScene.addObserver(this);
        myStage.setScene(myWelcomeScene.getScene());
    }

    private void showGeneralSettingScene () {
        myGeneralSettingScene = new GeneralSettingScene((GeneralSettingsConfiguring) this);
        myStage.setScene(myGeneralSettingScene.getScene());
    }

    private void showGSONWritingScene () {
        myGSONWritingScene = new GSONWritingScene(new BorderPane());
        myStage.setScene(myGSONWritingScene);
        myStage.setTitle("Writing Game");
        GSON_WRITER.writeGameFile(myTowerGroups, myLevels, gameDir);
        writeBackgroundImageToGameDir();
    }

    private void writeBackgroundImageToGameDir () {
        try {
            File file = new File(myBackgroundImageFileName);
            File backgroundDir = new File(AuthorController.gameDir
                                          + "background");
            backgroundDir.mkdir();
            File targetFile = new File(backgroundDir.getPath() + "/"
                                       + (new File(myBackgroundImageFileName)).getName());
            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
        }
        catch (IOException e) {
            new ErrorPopup(
                           "Background image could not be written to the game file");
        }
    }

    @Override
    public void makeDirectory (String gameName, String gameType) {
        gameDir = "./" + gameType + "Games/" + gameName + "/";
        File dir = new File(gameDir);
        dir.mkdir();
        showPathBuildingScene();
    }

    @Override
    public void configurePath (Path path) {
        myBackendRoutes = BackendRoutesGenerator.getBackendRoutes(path);
        showEnemyBuildingScene();
    }

    @Override
    public void configureEnemies (List<BaseEnemy> enemies) {
        myEnemies = enemies;
        if (notEnoughEnemies()) {
            new ErrorPopup(Constants.NOT_ENOUGH_ENEMIES_MSG);
        }
        else {
            showTowerBuildingScene();
        }
    }

    private boolean notEnoughTowers () {
        return myTowerGroups.size() < 1;
    }

    private boolean notEnoughEnemies () {
        return myEnemies.size() < 1;
    }

    @Override
    public void configureTowers (List<TowerUpgradeGroup> towers) {
        myTowerGroups = towers;
        if (notEnoughTowers()) {
            new ErrorPopup(Constants.NOT_ENOUGH_TOWERS_MSG);
        }
        else {
            showLevelBuildingScene();
        }
    }

    @Override
    public void configureLevels (List<BaseLevel> levels) {
        myLevels = levels;
        showGSONWritingScene();
    }

    @Override
    public List<BaseEnemy> fetchEnemies () {
        return myEnemies;
    }

    @Override
    public List<BackendRoute> fetchEnemyRoutes () {
        return myBackendRoutes;
    }

    @Override
    public void setBackground (String imageFileName) {
        myBackgroundImageFileName = imageFileName;
    }

    @Override
    public void setTowerRegions (boolean[][] backendTowerRegions) {     
        GSON_WRITER.writeTowerRegions(gameDir, backendTowerRegions);
    }

    @Override
    public void setGeneralSettings (GeneralSettingsWrapper wrapper) {
       GSON_WRITER.writeGeneralSettings(gameDir, wrapper);
    }

    /**
     * Called after welcome scene clicked.
     */
    @Override
    public void update (Observable o, Object arg) {
        this.showGeneralSettingScene();
    }
}

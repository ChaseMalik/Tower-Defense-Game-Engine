package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.mainclasses.Constants;
import gameAuthoring.mainclasses.controllerInterfaces.LevelConfiguring;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.BuildingSceneMenu;
import java.util.Observable;
import java.util.Observer;
import utilities.multilanguage.MultiLanguageUtility;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;


/**
 * Allows the user to build a new level.
 * 
 * @author Austin Kyker
 *
 */
public class LevelBuildingScene extends BuildingScene implements Observer {

    private static final String TITLE = "Level";

    private LevelBuildingDisplay myLevelsDisplay;
    private LevelConfiguring myLevelConfiguringController;

    public LevelBuildingScene (BorderPane root, LevelConfiguring controller) {
        super(root, TITLE);
        myLevelConfiguringController = controller;
        createMenuAndAddNewLevelOption();
        setupLevelDisplay();
    }

    private void setupLevelDisplay () {
        myLevelsDisplay = new LevelBuildingDisplay(myLevelConfiguringController.fetchEnemies());
        myPane.setCenter(myLevelsDisplay);
    }

    private void createMenuAndAddNewLevelOption () {
        BuildingSceneMenu menu = new BuildingSceneMenu();
        MenuItem newLevelItem = new MenuItem();
        newLevelItem.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty(Constants.NEW_LEVEL));
        newLevelItem.setOnAction(event -> myLevelsDisplay.addLevel());
        menu.addMenuItemToFileMenu(newLevelItem);
        menu.addObserver(this);
        myPane.setTop(menu.getNode());
    }

    /**
     * This is called when the user hits finished from the file menu.
     */
    @Override
    public void update (Observable arg0, Object arg1) {
        if (myLevelsDisplay.isAllUserInputIsValid()) {
            myLevelConfiguringController.configureLevels(myLevelsDisplay.transformToLevels());
        }
    }
}

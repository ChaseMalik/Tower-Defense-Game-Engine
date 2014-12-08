package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.mainclasses.controllerInterfaces.ILevelConfiguring;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.BuildingSceneMenu;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utilities.errorPopup.ErrorPopup;
import utilities.multilanguage.MultiLanguageUtility;


/**
 * Allows the user to build a new level.
 * 
 * @author Austin Kyker
 *
 */
public class LevelBuildingScene extends BuildingScene implements Observer {

    private static final String TITLE = "Level";

    private LevelBuildingDisplay myLevelsDisplay;
    private ILevelConfiguring myLevelConfiguringController;
    private Pane mySimulationPane;
    
    public LevelBuildingScene (BorderPane root, ILevelConfiguring controller) {
        super(root, TITLE);
        myLevelConfiguringController = controller;
        createMenuAndAddNewLevelOption();
        setupLevelDisplay();
        setupSimulationPane();
    }

    private void setupLevelDisplay () {
    	mySimulationPane = new Pane();
    	mySimulationPane.setPrefHeight(AuthorController.SCREEN_HEIGHT);
    	mySimulationPane.setPrefWidth(AuthorController.SCREEN_WIDTH);
       	myPane.setCenter(mySimulationPane);
       	
        myLevelsDisplay = new LevelBuildingDisplay(myLevelConfiguringController.fetchEnemies(), mySimulationPane);
        myPane.setLeft(myLevelsDisplay);
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
    
    private void setupSimulationPane(){

 
    }

    /**
     * This is called when the user hits finished from the file menu.
     */
    @Override
    public void update (Observable arg0, Object arg1) {
        if (myLevelsDisplay.isAllUserInputIsValid()) {
            myLevelConfiguringController.configureLevels(myLevelsDisplay.transformToLevels());
        }
        else {
            new ErrorPopup(Constants.LEVEL_ERROR);
        }
    }
}

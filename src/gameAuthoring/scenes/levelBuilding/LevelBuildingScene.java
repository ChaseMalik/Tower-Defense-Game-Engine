package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.mainclasses.controllerInterfaces.LevelConfiguring;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.BuildingSceneMenu;
import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * Allows the user to build a new level.
 * @author Austin Kyker
 *
 */
public class LevelBuildingScene extends BuildingScene implements Observer {
    
    private static final String TITLE = "Level";
    
    private ObservableList<BaseLevel>  myLevels;
    private LevelBuildingDisplay myLevelsDisplay;
    private LevelConfiguring myLevelConfiguringController;

    public LevelBuildingScene (BorderPane root, LevelConfiguring controller) {
        super(root, TITLE);
        myLevelConfiguringController = controller;
        myLevels = FXCollections.observableArrayList();
        createMenuAndAddNewLevelOption();     
        setupLevelDisplay();    
    }

    private void setupLevelDisplay () {
        myLevelsDisplay = new LevelBuildingDisplay(myLevelConfiguringController.fetchEnemies(),
                                                   myLevels);
        myLevelsDisplay.addLevel(new BaseLevel());
        myPane.setCenter(myLevelsDisplay);        
    }

    private void createMenuAndAddNewLevelOption () {
        BuildingSceneMenu menu = new BuildingSceneMenu();
        MenuItem newLevelItem = new MenuItem("New Level");
        newLevelItem.setOnAction(event->myLevelsDisplay.addLevel(new BaseLevel()));
        menu.addMenuItemToFileMenu(newLevelItem);
        menu.addObserver(this);
        myPane.setTop(menu.getNode());
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        myLevelConfiguringController.configureLevels(myLevels);    
    }
}

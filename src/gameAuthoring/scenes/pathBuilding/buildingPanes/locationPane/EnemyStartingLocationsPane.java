package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import utilities.multilanguage.MultiLanguageUtility;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


/**
 * Allows the user to place starting locations of the enemies on the screen.
 * 
 * @author Austin Kyker
 *
 */
public class EnemyStartingLocationsPane extends EnemyLocationPane {

    private Button mySaveStartsButton;
    private PathBuildingScene myPathBuildingScene;

    public EnemyStartingLocationsPane (Group group, Path path, 
                                       PathBuildingScene pathBuildingScene) {
        super(group, path);
        mySaveStartsButton = new Button();
        mySaveStartsButton.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.SET_START_LOCS));
        myPathBuildingScene = pathBuildingScene;
        createEnemyStartingLocationsSetupComponents();
        this.setOnMouseClicked(event -> addNewStartingLocation(event));
    }

    private void createEnemyStartingLocationsSetupComponents () {
        super.createEnemyLocationsSetupComponents(mySaveStartsButton,
                                                  Constants.ENEMY_START_LOC_PROMPT);
        mySaveStartsButton.setOnAction(event -> myPathBuildingScene
                .proceedToEndLocationsSelection());
        myClearLocations.setOnAction(event -> myPath.clearEnemyStartingLocations());
    }

    private void addNewStartingLocation (MouseEvent event) {
        PathStartingLocation createdStartingLoc =
                myPath.addStartingLocation(event.getX(), event.getY());
        if (createdStartingLoc != null) {
            drawLocation(createdStartingLoc);
        }
    }
}

package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import utilities.multilanguage.MultiLanguageUtility;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


/**
 * Allows the user to place ending locations on the screen.
 * 
 * @author Austin Kyker
 *
 */
public class EnemyEndingLocationsPane extends EnemyLocationPane {

    private Button mySaveEndsButton;
    private PathBuildingScene myPathBuildingScene;

    public EnemyEndingLocationsPane (Group group, Path path, PathBuildingScene pathBuildingScene) {
        super(group, path);
        mySaveEndsButton = new Button();
        mySaveEndsButton.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.SET_END_LOCS));
        myPathBuildingScene = pathBuildingScene;
        createEnemyEndingLocationsSetupComponents();
        this.setOnMouseClicked(event -> addNewEndingLocation(event));
    }

    private void createEnemyEndingLocationsSetupComponents () {
        super.createEnemyLocationsSetupComponents(mySaveEndsButton,
                                                  Constants.ENEMY_ENDING_LOC_PROMPT);
        mySaveEndsButton.setOnAction(event -> myPathBuildingScene
                .proceedToLineDrawerModeIfLocationsVerified());
        myClearLocations.setOnAction(event -> myPath.clearEnemyEndingLocations());
    }

    private void addNewEndingLocation (MouseEvent event) {
        PathEndingLocation createdEndingLoc = 
                myPath.addEndingLocation(event.getX(), event.getY());
        if (createdEndingLoc != null) {
            drawLocation(createdEndingLoc);
        }
    }

}

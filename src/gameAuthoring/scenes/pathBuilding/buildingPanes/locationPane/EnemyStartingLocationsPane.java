package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import utilities.multilanguage.MultiLanguageUtility;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


/**
 * Allows the user to place starting locations on the screen.
 * 
 * @author Austin Kyker
 *
 */
public class EnemyStartingLocationsPane extends EnemyLocationPane {

    private Button mySaveStartsButton;
    private PathBuildingScene myPathBuildingScene;

    public EnemyStartingLocationsPane (Group group, Path path, PathBuildingScene pathBuildingScene) {
        super(group, path);
        mySaveStartsButton = new Button();
        mySaveStartsButton.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("SetStartLocations"));
        myPathBuildingScene = pathBuildingScene;
        createEnemyStartingLocationsSetupComponents();
        this.setOnMouseClicked(event -> addNewStartingLocation(event));
    }

    private void createEnemyStartingLocationsSetupComponents () {
        super.createEnemyLocationsSetupComponents(mySaveStartsButton, "EnemyStartingLocationPrompt");
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

package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class EnemyEndingLocationsPane extends EnemyLocationPane {
    
    private static final String LABEL_TEXT = "Click to add a End Location";
    private static final String SET_BTN_TEXT = "Set End Locations";
    private Button mySaveEndsButton = new Button(SET_BTN_TEXT);
    private PathBuildingScene myPathBuildingScene;
    
    public EnemyEndingLocationsPane(Group group, Path path, PathBuildingScene pathBuildingScene){
        super(group, path);
        myPathBuildingScene = pathBuildingScene;
        createEnemyEndingLocationsSetupComponents();
        this.setOnMouseClicked(event->addNewEndingLocation(event));
    }

    private void createEnemyEndingLocationsSetupComponents () {
        super.createEnemyLocationsSetupComponents(mySaveEndsButton, LABEL_TEXT);
        mySaveEndsButton.setOnAction(event->myPathBuildingScene.proceedToLineDrawerModeIfLocationsVerified());
        myClearLocations.setOnAction(event->myPath.clearEnemyEndingLocations());
    }
    
    private void addNewEndingLocation(MouseEvent event){
        PathEndingLocation createdEndingLoc = myPath.addEndingLocation(event.getSceneX(), event.getSceneY());
        if(createdEndingLoc != null){
            drawLocation(createdEndingLoc);
        }
    }

}

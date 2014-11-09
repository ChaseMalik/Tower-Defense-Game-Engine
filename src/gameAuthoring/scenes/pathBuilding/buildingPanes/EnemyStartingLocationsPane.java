package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EnemyStartingLocationsPane extends BuildingPane {
    
    private Button mySaveStartsButton;
    private Label mySaveStartsLabel;
    private PathBuildingScene myPathBuildingScene;
    private Path myPath;
    
    public EnemyStartingLocationsPane(Group group, Path path, PathBuildingScene pathBuildingScene) {
        super(group);
        myPath = path;
        myPathBuildingScene = pathBuildingScene;
        createEnemyStartingLocationsSetupComponents();
        this.setOnMouseClicked(event->addNewStartingLocation(event));
    }
    
    private void addNewStartingLocation (MouseEvent event) {
        PathStartingLocation createdStartingLoc = myPath.addStartingLocation(event.getSceneX(), event.getSceneY());
        if(createdStartingLoc != null){
            drawLocation(createdStartingLoc);
        }
    }

    private void createEnemyStartingLocationsSetupComponents () {
        mySaveStartsButton = new Button("Set Start Locations");
        mySaveStartsButton.setOnAction(event->myPathBuildingScene.proceedToEndLocationsSelection());
        mySaveStartsLabel = new Label("Click to add start locations");
        mySaveStartsLabel.setLayoutX(190);
        mySaveStartsLabel.setLayoutY(270);
        mySaveStartsButton.setLayoutX(200);
        mySaveStartsButton.setLayoutY(300);
        this.getChildren().addAll(mySaveStartsLabel, mySaveStartsButton);
    }
}

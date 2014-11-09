package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EnemyEndingLocationsPane extends BuildingPane {
    
    private Button mySaveEndsButton;
    private Label mySaveEndsLabel;
    private Path myPath;
    private PathBuildingScene myPathBuildingScene;
    
    public EnemyEndingLocationsPane(Group group, Path path, PathBuildingScene pathBuildingScene){
        super(group);
        myPath = path;
        myPathBuildingScene = pathBuildingScene;
        createEnemyEndingLocationsSetupComponents();
        this.setOnMouseClicked(event->addNewEndingLocation(event));
    }

    private void createEnemyEndingLocationsSetupComponents () {
        mySaveEndsButton = new Button("Set End Locations");
        mySaveEndsButton.setOnAction(event->myPathBuildingScene.proceedToLineDrawerMode());
        mySaveEndsLabel = new Label("Click to add end locations");
        mySaveEndsLabel.setLayoutX(190);
        mySaveEndsLabel.setLayoutY(270);
        mySaveEndsButton.setLayoutX(200);
        mySaveEndsButton.setLayoutY(300);
        this.getChildren().addAll(mySaveEndsLabel, mySaveEndsButton);
    }
    
    private void addNewEndingLocation(MouseEvent event){
        PathEndingLocation createdEndingLoc = myPath.addEndingLocation(event.getSceneX(), event.getSceneY());
        if(createdEndingLoc != null){
            drawLocation(createdEndingLoc);
        }
    }

}

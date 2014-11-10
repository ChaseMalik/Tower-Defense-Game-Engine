package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setLayoutX(BuildingPane.DRAW_SCREEN_WIDTH/2-50);
        container.setLayoutY(AuthorController.SCREEN_HEIGHT/2);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: LightGray; -fx-opacity: 0.95");
        mySaveStartsButton = new Button("Set Start Locations");
        mySaveStartsButton.setOnAction(event->myPathBuildingScene.proceedToEndLocationsSelection());
        mySaveStartsLabel = new Label("Click to add start locations");
        container.getChildren().addAll(mySaveStartsLabel, mySaveStartsButton);
        this.getChildren().add(container);
    }
}

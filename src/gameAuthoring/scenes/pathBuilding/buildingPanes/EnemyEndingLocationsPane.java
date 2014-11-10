package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setLayoutX(BuildingPane.DRAW_SCREEN_WIDTH/2-50);
        container.setLayoutY(AuthorController.SCREEN_HEIGHT/2);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: LightGray; -fx-opacity: 0.95");
        mySaveEndsButton = new Button("Set End Locations");
        mySaveEndsButton.setOnAction(event->myPathBuildingScene.proceedToLineDrawerMode());
        mySaveEndsLabel = new Label("Click to add end locations");
        container.getChildren().addAll(mySaveEndsLabel, mySaveEndsButton);
        this.getChildren().add(container);
    }
    
    private void addNewEndingLocation(MouseEvent event){
        PathEndingLocation createdEndingLoc = myPath.addEndingLocation(event.getSceneX(), event.getSceneY());
        if(createdEndingLoc != null){
            drawLocation(createdEndingLoc);
        }
    }

}

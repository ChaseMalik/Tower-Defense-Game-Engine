package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;

public abstract class EnemyLocationPane extends BuildingPane {

    private static final int CONTAINER_PADDING = 15;
    private static final String CLR_BTN_TEXT = "Clear End Locations";
    
    protected Button myClearLocations = new Button(CLR_BTN_TEXT);
    protected Path myPath;

    public EnemyLocationPane (Group group, Path path) {
        super(group);
        myPath = path;
    }
    
    protected void createEnemyLocationsSetupComponents (Button button, String LabelStr) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(CONTAINER_PADDING));
        container.setLayoutX(BuildingPane.DRAW_SCREEN_WIDTH/2-50);
        container.setLayoutY(AuthorController.SCREEN_HEIGHT/2);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: LightGray; -fx-opacity: 0.95");
        Label label = new Label(LabelStr);
        container.getChildren().addAll(label, button, myClearLocations);
        this.getChildren().add(container);
    }
}

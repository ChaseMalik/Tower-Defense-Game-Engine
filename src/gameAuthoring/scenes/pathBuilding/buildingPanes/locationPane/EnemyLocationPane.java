package gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utilities.multilanguage.MultiLanguageUtility;


/**
 * Represent a node representing an enemy location. Also defines a pane that
 * allows the user to clear the locations as well as to continue to the next
 * scene.
 * 
 * @author Austin Kyker
 *
 */
public abstract class EnemyLocationPane extends BuildingPane {

    private static final double PROMPT_HEIGHT = AuthorController.SCREEN_HEIGHT / 2 - 100;
    private static final double PROMPT_WIDTH = BuildingPane.DRAW_SCREEN_WIDTH / 2 - 80;
    private static final int CONTAINER_PADDING = 15;

    protected Button myClearLocations;
    protected Path myPath;

    public EnemyLocationPane (Group group, Path path) {
        super(group);
        myClearLocations = new Button();
        myClearLocations.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.CLEAR_LOCS));
        myPath = path;
    }

    protected void createEnemyLocationsSetupComponents (Button button, String labelStr) {
        VBox container = new VBox(Constants.SM_PADDING);
        container.setPadding(new Insets(CONTAINER_PADDING));
        container.setLayoutX(PROMPT_WIDTH);
        container.setLayoutY(PROMPT_HEIGHT);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: LightGray; -fx-opacity: 0.98;");
        Label label = new Label();
        label.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty(labelStr));
        container.getChildren().addAll(label, button, myClearLocations);
        this.getChildren().add(container);
    }
}

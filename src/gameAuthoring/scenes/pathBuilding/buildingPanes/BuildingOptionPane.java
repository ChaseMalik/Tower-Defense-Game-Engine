package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.multilanguage.MultiLanguageUtility;


/**
 * Represents the pane on the right side of the PathBuildingScene that has the different
 * types of drawing options. Clicking on these options switches out the BuildingPanes.
 * 
 * @author Austin Kyker
 *
 */
public class BuildingOptionPane extends VBox {

    private static final String BORDER_CSS = "border";
    private static final String SELECTED_CSS = "selected";
    private static final String COMP_OPTION_CSS = "pathComponentOption";
    private static final String DRAWING_OPTIONS_IMG_DIR =
            "./src/gameAuthoring/Resources/PathDrawingOptionsImages/";
    private static final double OPTIONS_IMAGE_WIDTH =
            PathBuildingScene.SIDE_PANE_WIDTH - 45;
    private static final int OPTIONS_IMAGE_HEIGHT = 40;
    

    public BuildingOptionPane (String componentName) {
        setupOptionsBoxGraphicallyAndDisplayLabel(componentName);
        ImageView imageView = 
                StringToImageViewConverter.getImageView(OPTIONS_IMAGE_WIDTH, 
                                                        OPTIONS_IMAGE_HEIGHT, 
                                                        DRAWING_OPTIONS_IMG_DIR + 
                                                        componentName.toLowerCase() + 
                                                        ".png");
        this.getChildren().add(imageView);
        this.getStyleClass().add(BORDER_CSS);
    }

    public void selectPane () {
        if (!this.getStyleClass().contains(SELECTED_CSS)) {
            this.getStyleClass().add(SELECTED_CSS);
        }
    }

    private void setupOptionsBoxGraphicallyAndDisplayLabel (String componentName) {
        this.setPadding(new Insets(PathBuildingScene.BUILDING_OPTIONS_PADDING));
        Label nameLabel = new Label(componentName);
        nameLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                                      .getStringProperty(componentName));
        this.getChildren().add(nameLabel);
        this.getStyleClass().add(COMP_OPTION_CSS);
    }
}
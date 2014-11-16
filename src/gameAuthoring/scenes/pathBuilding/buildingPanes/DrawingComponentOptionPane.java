package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utilities.ErrorPopup;


public class DrawingComponentOptionPane extends VBox {

    private static final String DRAWING_OPTIONS_IMG_DIR = 
            "./src/gameAuthoring/Resources/PathDrawingOptionsImages/";
    private static final double OPTIONS_IMAGE_WIDTH = 
            PathBuildingScene.PATH_BUILDING_OPTIONS_WIDTH - 3*PathBuildingScene.BUILDING_OPTIONS_PADDING;
    private static final int OPTIONS_IMAGE_HEIGHT = 103;

    public DrawingComponentOptionPane(String componentName){
        setupOptionsBoxGraphicallyAndDisplayLabel(componentName);
        try {
            ImageView imageView = new ImageView();
            File imgFile = new File(DRAWING_OPTIONS_IMG_DIR + componentName.toLowerCase() + ".png");
            imageView.setImage(new Image(new FileInputStream(imgFile), OPTIONS_IMAGE_WIDTH, 
                                         OPTIONS_IMAGE_HEIGHT, false, true));
            this.getChildren().add(imageView);
        }
        catch (FileNotFoundException e) {
            new ErrorPopup("No file found representing " + componentName + " image.");
        }
        this.setOnMouseClicked(event->selectPane());
    }


    private void selectPane () {
        if(!this.getStyleClass().contains("selected")){
            this.getStyleClass().add("selected");
        }
    }

    private void setupOptionsBoxGraphicallyAndDisplayLabel (String componentName) {
        this.setPadding(new Insets(PathBuildingScene.BUILDING_OPTIONS_PADDING));
        this.getChildren().add(new Label(componentName));
        this.getStyleClass().add("pathComponentOption");
    }
}
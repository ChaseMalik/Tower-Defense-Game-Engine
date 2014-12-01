package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BackgroundBuilding;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.StringToImageViewConverter;

/**
 * A pane of default maps that the user can select as their background. This pane
 * will be on the left of the PathBuildingScene. Users can also drag and drop image files
 * on the drag and drop file pane in the center of the screen during this background
 * selection process.
 * @author Austin Kyker
 *
 */
public class DefaultMapSelectionPane {

    private static final String DEFAULT_MAP_IMAGES_DIR = 
            "./src/gameAuthoring/Resources/DefaultMapImages/";
    private static final double IMAGE_PADDING = 10.0;
    private static final double IMAGE_WIDTH = 120.0;
    private static final double IMAGE_HEIGHT = 80.0;
    private static final double SCROLLPANE_WIDTH = IMAGE_WIDTH + 2*IMAGE_PADDING + 10;


    private ScrollPane myScrollPane;
    private VBox myImageDisplayVBox;
    private BackgroundBuilding myPathBackgroundBuildingController;



    public DefaultMapSelectionPane(BackgroundBuilding controller){
        myPathBackgroundBuildingController = controller;
        myScrollPane = new ScrollPane();
        myScrollPane.setMaxHeight(AuthorController.SCREEN_HEIGHT);
        myScrollPane.setPrefWidth(PathBuildingScene.SIDE_PANE_WIDTH);
        File mapDirectory = new File(DEFAULT_MAP_IMAGES_DIR);

        File[] defaultMapImages = mapDirectory.listFiles();

        myImageDisplayVBox = new VBox(IMAGE_PADDING);
        myImageDisplayVBox.setPadding(new Insets(IMAGE_PADDING));

        for(File f:defaultMapImages){
            ImageView imgView = StringToImageViewConverter.getImageView(IMAGE_WIDTH, 
                                                                        IMAGE_HEIGHT, 
                                                                        f.getPath());
            imgView.setOnMouseClicked(event -> handleImageClick(f));
            myImageDisplayVBox.getChildren().add(imgView);		
        }

        myScrollPane.setContent(myImageDisplayVBox);
        myScrollPane.setPrefWidth(SCROLLPANE_WIDTH);
    }

    private void handleImageClick(File fileCorrespondingToMapSelected) {
        myPathBackgroundBuildingController.setBackground(fileCorrespondingToMapSelected.getPath());
    }

    public ScrollPane getDefaultMapsScrollPane(){
        return myScrollPane;
    }	
}
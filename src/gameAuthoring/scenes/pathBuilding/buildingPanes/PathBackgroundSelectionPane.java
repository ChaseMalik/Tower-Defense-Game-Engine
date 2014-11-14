package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Utilities.DragAndDropFilePane;

public class PathBackgroundSelectionPane extends BuildingPane implements Observer {

    private DragAndDropFilePane myDragAndDropPane;
    private PathBuildingScene myPathBuildingScene;

    public PathBackgroundSelectionPane (Group group, PathBuildingScene pathBuildingScene) {
        super(group);
        myPathBuildingScene = pathBuildingScene;
        String[] validExtensions = new String[]{".jpg", ".png", ".jpeg"};
        String fileDestinationDir = "./PathBackgrounds/";
        myDragAndDropPane = new DragAndDropFilePane(BuildingPane.DRAW_SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT, 
                                                    validExtensions, fileDestinationDir);

        myDragAndDropPane.addObserver(this);
        this.getChildren().add(myDragAndDropPane.getPane());
    }

    @Override
    public void update (Observable o, Object arg1) {
        FileInputStream imageFileStream = myDragAndDropPane.getImageStream();
        if(imageFileStream != null){
            ImageView imageView = new ImageView();
            Image image = new Image(imageFileStream, BuildingPane.DRAW_SCREEN_WIDTH, 
                                    AuthorController.SCREEN_HEIGHT, false, true);
            imageView.setImage(image);
            myGroup.getChildren().add(imageView);   
            myPathBuildingScene.proceedToStartLocationSelection();
        }
    }
}

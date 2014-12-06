package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import utilities.JavaFXutilities.DragAndDropFilePanes.DragAndDropAudioPane;
import utilities.JavaFXutilities.DragAndDropFilePanes.DragAndDropNoCopyImagePane;

/**
 * Represents the pane where the user can drag and drop a file to act as the background
 * for the game. Uses the DragAndDropFilePane utility.
 * @author Austin Kyker
 *
 */
public class PathBackgroundSelectionPane extends BuildingPane implements Observer {

    private DragAndDropAudioPane myDragAndDropPane;
    private BackgroundBuilding myBackgroundBuildingController;

    public PathBackgroundSelectionPane (Group group, BackgroundBuilding controller) {
        super(group);
        myBackgroundBuildingController = controller;
        myDragAndDropPane = new DragAndDropNoCopyImagePane(BuildingPane.DRAW_SCREEN_WIDTH, 
                                                           AuthorController.SCREEN_HEIGHT);
        myDragAndDropPane.addObserver(this);
        this.getChildren().add(myDragAndDropPane.getPane());
    }

    @Override
    public void update (Observable o, Object arg1) {
        myBackgroundBuildingController.setBackground((String) arg1);
    }
}

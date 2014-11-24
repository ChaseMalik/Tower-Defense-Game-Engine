package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.PathBuildingScene;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import utilities.DragAndDropFilePanes.DragAndDropImagePane;

/**
 * Represents the pane where the user can drag and drop a file to act as the background
 * for the game. Uses the DragAndDropFilePane utility.
 * @author Austin Kyker
 *
 */
public class PathBackgroundSelectionPane extends BuildingPane implements Observer {

    private DragAndDropImagePane myDragAndDropPane;
    private PathBuildingScene myPathBuildingScene;

    public PathBackgroundSelectionPane (Group group, PathBuildingScene pathBuildingScene) {
        super(group);
        myPathBuildingScene = pathBuildingScene;
        myDragAndDropPane = new DragAndDropImagePane(BuildingPane.DRAW_SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT, 
                                                     AuthorController.gameDir);

        myDragAndDropPane.addObserver(this);
        this.getChildren().add(myDragAndDropPane.getPane());
    }

    @Override
    public void update (Observable o, Object arg1) {
        myGroup.getChildren().add(myDragAndDropPane.getImageView());
        myPathBuildingScene.proceedToStartLocationSelection();
    }
}

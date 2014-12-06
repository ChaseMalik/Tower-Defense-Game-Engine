package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * This BuildingPane is important. In the PathBuildingScene it defines
 * a unique functionality or option. For instance, one option might be line drawing
 * and another might be selection mode. In each of these cases, these panes
 * act different and must implement their own on-click action. Notice that of the panes
 * share a common group so that what is on the screen even when the panes switch.
 * @author Austin Kyker
 *
 */
public class BuildingPane extends Pane {

    public static final double DRAW_SCREEN_WIDTH_RATIO = .80;
    public static final double DRAW_SCREEN_WIDTH = 
            AuthorController.SCREEN_WIDTH * DRAW_SCREEN_WIDTH_RATIO;
    private static final String BUILD_SCREEN_CSS_CLASS = "buildScreen";
    protected static Group myGroup;


    public BuildingPane(Group group) {
        myGroup = group;
        this.getChildren().add(myGroup);
        this.setPrefWidth(DRAW_SCREEN_WIDTH);
        this.getStyleClass().add(BUILD_SCREEN_CSS_CLASS);
    }

    public void drawLocation(Node component) {
        myGroup.getChildren().add(component);
        refreshScreen();
    }

    public void drawPathComponent(Node component) {
        myGroup.getChildren().add(1, component);
        refreshScreen();
    }

    public void refreshScreen() {
        this.getChildren().remove(myGroup);
        this.getChildren().add(0, myGroup);
    }

    public void removeComponentFromScreen (Node component) {
        myGroup.getChildren().remove(component);       
    }
    
    /**
     * Executed when a new building pane is selected and the currently selected pane
     * will no longer be selected. Default function is to do nothing.
     * Overridden in CurveDrawingPane.
     */
    public void executeExitFunction () {}

    /**
     * Executed when building pane is selected and becomes currently selected pane. Default
     * function is to do nothing. Overridden in CurveDrawingPane.
     */
    public void executeEnterFunction () {}
}

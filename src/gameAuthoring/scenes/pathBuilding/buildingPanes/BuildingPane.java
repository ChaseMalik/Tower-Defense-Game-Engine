package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BuildingPane extends Pane {

    public static final double DRAW_SCREEN_WIDTH_RATIO = .84;
    protected static final double DRAW_SCREEN_WIDTH = 
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

    public void removeConnectedComponentFromScreen (List<PathComponent> deletedComponent) {
        myGroup.getChildren().removeAll(deletedComponent);
        
    }
}

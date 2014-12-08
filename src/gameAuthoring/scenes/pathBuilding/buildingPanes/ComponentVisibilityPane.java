package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import javafx.scene.Group;

/**
 * A pane to allow the user to select which path components should be visible
 * and invisible. An invisible path component means that the enemy will be hidden
 * from the screen on this path (maybe the enemy went into a tunnel).
 * @author Austin Kyker
 *
 */
public class ComponentVisibilityPane extends BuildingPane {

    private Path myPath;

    public ComponentVisibilityPane (Group group, Path path) {
        super(group);
        myPath = path;
    }

    private void addListeners () {
        for (PathComponent comp : myPath.getAllPathComponents()) {
            comp.getNode().setOnMouseClicked(event -> comp.togglePathComponentVisibility());
        }
    }

    private void removeListeners () {
        for (PathComponent comp : myPath.getAllPathComponents()) {
            comp.getNode().setOnMouseClicked(null);
        }
    }

    @Override
    public void executeEnterFunction () {
        addListeners();
        for (PathComponent comp : myPath.getAllPathComponents()) {
            comp.showVisibility();
        }
    }

    @Override
    public void executeExitFunction () {
        removeListeners();
        for (PathComponent comp : myPath.getAllPathComponents()) {
            comp.removeStroke();
        }
    }
}

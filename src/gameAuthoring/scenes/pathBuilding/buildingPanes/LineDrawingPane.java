package gameAuthoring.scenes.pathBuilding.buildingPanes;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathLine;

/**
 * Defines the pane where the user can draw lines and the click-listener
 * to actually create these lines. Click once to define the starting location of the line
 * and click again to define the ending location of the line.
 * @author Austin Kyker
 *
 */
public class LineDrawingPane extends BuildingPane {
    
    private static final double MIN_LINE_LENGTH = 30;

    private PathLine myLineBeingCreated;
    private Path myPath;

    public LineDrawingPane(Group group, Path path){
        super(group);
        myPath = path;
        addListeners();
    }

    private void addListeners () {
        this.setOnMousePressed(event->handleBuildScreenClick(event));

        this.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                stretchLineToEndAtCurrentMousePosition(event);
            }     
        });
    }
    
    private void removeListeners() {
        this.setOnMousePressed(null);
        this.setOnMouseMoved(null);
    }

    private void stretchLineToEndAtCurrentMousePosition (MouseEvent event) {
        if(myLineBeingCreated != null){
            myLineBeingCreated.setEndX(event.getX());
            myLineBeingCreated.setEndY(event.getY());
        }
    }   

    private void handleBuildScreenClick(MouseEvent event) {
        if(myLineBeingCreated == null){
            myLineBeingCreated = new PathLine(event.getX(), event.getY());
            drawPathComponent(myLineBeingCreated);
            myPath.addComponentToPath(myLineBeingCreated);
        }
        else {
            super.removeComponentFromScreen(myLineBeingCreated);
            if(myLineBeingCreated.getLength() > MIN_LINE_LENGTH){
                PathLine tempLine = myLineBeingCreated;
                drawPathComponent(tempLine);
                myPath.tryToAddConnectComponentToEndingLocation(tempLine);
            }
            myLineBeingCreated = null;
        }
    }
    
    @Override
    public void executeEnterFunction() {
        addListeners();
    }

    @Override
    public void executeExitFunction() {
        removeListeners();
    }
}
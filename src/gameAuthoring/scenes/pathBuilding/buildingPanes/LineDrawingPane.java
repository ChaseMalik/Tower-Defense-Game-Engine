package gameAuthoring.scenes.pathBuilding.buildingPanes;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import gameAuthoring.scenes.pathBuilding.Path;
import gameAuthoring.scenes.pathBuilding.PathLine;

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
            myPath.addPathComponentToPath(myLineBeingCreated);
        }
        else {
            super.removeFromGroup(myLineBeingCreated);
            if(myLineBeingCreated.getLength() > MIN_LINE_LENGTH){
                PathLine tempLine = myLineBeingCreated;
                drawPathComponent(tempLine);
                myPath.tryToConnectComponentEndToEndLocation(tempLine);
            }
            myLineBeingCreated = null;
        }
    }
}
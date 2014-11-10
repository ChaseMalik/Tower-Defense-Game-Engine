package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathLine;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class SelectComponentPane extends BuildingPane {

    private Path myPath;
    private double mouseX;
    private double mouseY;

    public SelectComponentPane (Group group, Path path) {
        super(group);
        myPath = path;        
    }
    
    public void addListenersToComponents() {
        for(PathComponent component:myPath.getAllPathComponents()){
            addSelectionListeners((PathLine) component);
        }
    }

    private void addSelectionListeners(PathLine component){
        component.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();   
            }

        });
        component.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                double deltaX = event.getSceneX() - mouseX;
                double deltaY = event.getSceneY() - mouseY;
                myPath.moveConnectedComponent(component, deltaX, deltaY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY(); 
            }                      
        });        
        component.setOnMouseReleased(event->handleSelectionAndTryToConnectComponents(component));
    }

    private void handleSelectionAndTryToConnectComponents (PathComponent component) {
        myPath.attemptToConnectComponents(component);
        myPath.handleComponentSelection(component);
    }
}
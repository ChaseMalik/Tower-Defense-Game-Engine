package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 * Allows the user to select a route by clicking on PathComponents.
 * When the user clicks on a component, the component becomes green and the user
 * can drag it to a new spot if it is not connected to a starting or ending location
 * or the user can remove it. 
 * @author Austin Kyker
 *
 */
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
            addSelectionListeners(component);
        }
    }
    
    public void removeListenerFromComponents() {
        for(PathComponent component:myPath.getAllPathComponents()){
            component.getNode().setOnMousePressed(null);
            component.getNode().setOnMouseDragged(null);
            component.getNode().setOnMouseReleased(null);
        }
    }

    private void addSelectionListeners(PathComponent component){
        component.getNode().setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();   
            }

        });
        component.getNode().setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                double deltaX = event.getSceneX() - mouseX;
                double deltaY = event.getSceneY() - mouseY;
                myPath.moveConnectedComponent(component, deltaX, deltaY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY(); 
            }                      
        });        
        component.getNode().setOnMouseReleased(event->handleSelectionAndTryToConnectComponents(component));
    }

    private void handleSelectionAndTryToConnectComponents (PathComponent component) {
        myPath.attemptToConnectRoutes(component);
        myPath.handleComponentSelection(component);
    }
    
    
    @Override
    public void executeEnterFunction() {
        addListenersToComponents();
    }

    @Override
    public void executeExitFunction() {
        removeListenerFromComponents();
    }
}
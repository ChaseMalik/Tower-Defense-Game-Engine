package gameAuthoring.scenes.pathBuilding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point2D;

public class Path {
    private static final double CONNECT_THRESHOLD = 40;

    private static final double INSIDE_STARTING_LOC_THRESHOLD = 40;
    
    private List<StartingLocation> myStartingLocations;    
    private List<EndingLocation> myEndingLocations;
    private List<LinkedList<PathComponent>> myPath;
    
    public Path() {
        myPath = new ArrayList<LinkedList<PathComponent>>();
        myStartingLocations = new ArrayList<StartingLocation>();
        myEndingLocations = new ArrayList<EndingLocation>();
    }
    
    public void addPathComponentToPath(PathComponent componentToAdd) {
        
        if(!componentSuccessfullyAddedToStartingLocation(componentToAdd) &&
           !componentSuccessfullyAddedToEndOfAConnectedComponent(componentToAdd)) {
            createNewConnectedComponent(componentToAdd);
        }
    }

    private boolean componentSuccessfullyAddedToStartingLocation (PathComponent componentToAdd) {
        for(StartingLocation startingLoc:myStartingLocations){
            Point2D centerCircle = new Point2D(startingLoc.getCenterX(), startingLoc.getCenterY());
            if(addedComponentIsWithinCircle(componentToAdd, centerCircle)) {
                componentToAdd.setStartingPoint(centerCircle);
                return true;
            }
        }
        return false;
    }

    private boolean addedComponentIsWithinCircle (PathComponent componentToAdd, Point2D centerCircle) {
        System.out.println(componentToAdd.getStartingPoint().distance(centerCircle));
        return componentToAdd.getStartingPoint().distance(centerCircle) < INSIDE_STARTING_LOC_THRESHOLD;
    }

    private boolean componentSuccessfullyAddedToEndOfAConnectedComponent (PathComponent componentToAdd) {
        for(LinkedList<PathComponent> connectedComponent:myPath){
            PathComponent lastComponentInConnectedComponent = connectedComponent.getLast();
            if(closeEnoughToConnect(lastComponentInConnectedComponent, componentToAdd)){
                componentToAdd.setStartingPoint(lastComponentInConnectedComponent.getEndingPoint());
                connectedComponent.add(componentToAdd);
                return true;
            }
        }
        return false;
    }

    private void createNewConnectedComponent (PathComponent componentToAdd) {
        LinkedList<PathComponent> newConnectedComponent = new LinkedList<PathComponent>();
        newConnectedComponent.add(componentToAdd);
        myPath.add(newConnectedComponent);
    }

    private boolean closeEnoughToConnect (PathComponent last, PathComponent componentToAdd) {
        return last.getEndingPoint().distance(componentToAdd.getStartingPoint()) < CONNECT_THRESHOLD;
    }

    public void moveConnectedComponent (PathComponent draggedComponent, double deltaX, double deltaY) {
        LinkedList<PathComponent> connectedComponent = 
                getConnectedComponentContaining(draggedComponent);
        for(PathComponent component:connectedComponent) {
            component.translate(deltaX, deltaY);
        }
    }

    private LinkedList<PathComponent> getConnectedComponentContaining (PathComponent draggedComponent) {
        for(LinkedList<PathComponent> connectedComponent:myPath){
            for(PathComponent component:connectedComponent) {
                if(draggedComponent.equals(component)) {
                    return connectedComponent;
                }
            }
        }
        return null;
    }

    public void tryToConnectComponents (PathComponent componentDragged) {
        LinkedList<PathComponent> draggedConnectedComponent = 
                getConnectedComponentContaining(componentDragged);
        for(LinkedList<PathComponent> connectedComponent:myPath){
            PathComponent lastComponentInConnectedComponent = connectedComponent.getLast();
            if(closeEnoughToConnect(lastComponentInConnectedComponent, draggedConnectedComponent.getFirst())){
                draggedConnectedComponent.getFirst().setStartingPoint(lastComponentInConnectedComponent.getEndingPoint());
                connectedComponent.addAll(draggedConnectedComponent);
                myPath.remove(draggedConnectedComponent);
                return;             
            }
        }   
    }
    
    public void addStartingLocation(StartingLocation loc) {
        myStartingLocations.add(loc);
    }
    
    public void addEndingLocation(EndingLocation loc) {
        myEndingLocations.add(loc);
    }

    public boolean startingLocationsConfiguredCorrectly () {
        return !myStartingLocations.isEmpty();
    }

    public boolean endingLocationsConfiguredCorrectly () {
        return !myEndingLocations.isEmpty();
    }
    
    
}

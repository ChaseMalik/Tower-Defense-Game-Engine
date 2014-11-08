package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point2D;

public class Path {

    private static final double CONNECT_THRESHOLD = 40;
    private static final double INSIDE_STARTING_LOC_THRESHOLD = 50;
    private static final double MIN_DISTANCE_BTW_LOCS = 150;
    private static final int MAX_NUM_STARTING_LOCS = 2;
    private static final int MAX_NUM_ENDING_LOCS = 2;

    private List<PathStartingLocation> myStartingLocations;    
    private List<PathEndingLocation> myEndingLocations;
    private List<LinkedList<PathComponent>> myPath;

    private PathComponent mySelectedComponent;

    public Path() {
        myPath = new ArrayList<LinkedList<PathComponent>>();
        myStartingLocations = new ArrayList<PathStartingLocation>();
        myEndingLocations = new ArrayList<PathEndingLocation>();
    }

    public void addComponentToPath(PathComponent componentToAdd) {
        if(!componentAddedToStartingLocation(componentToAdd) &&
           !componentAddedToEndOfExistingConnectedComponent(componentToAdd)) {
            createNewConnectedComponent(componentToAdd);
        }
    }

    private boolean componentAddedToStartingLocation (PathComponent componentToAdd) {
        for(PathStartingLocation startingLoc:myStartingLocations){
            Point2D centerOfStartingLoc = 
                    new Point2D(startingLoc.getCenterX(), startingLoc.getCenterY());
            if(addedComponentIsWithinCircle(componentToAdd.getStartingPoint(), centerOfStartingLoc)) {
                componentToAdd.setStartingPoint(centerOfStartingLoc);
                createNewConnectedComponent(componentToAdd);
                return true;
            }
        }
        return false;
    }

    public boolean tryToAddConnectComponentToEndingLocation (PathComponent componentToAdd) {
        for(PathEndingLocation endingLoc:myEndingLocations){
            Point2D centerCircle = new Point2D(endingLoc.getCenterX(), endingLoc.getCenterY());
            if(addedComponentIsWithinCircle(componentToAdd.getEndingPoint(), centerCircle)) {
                componentToAdd.setEndingPoint(centerCircle);
                createNewConnectedComponent(componentToAdd);
                return true;
            }
        }
        return false;
    }
    
    private boolean componentAddedToEndOfExistingConnectedComponent (PathComponent componentToAdd) {
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

    private boolean addedComponentIsWithinCircle (Point2D pointNearestCircle, Point2D centerCircle) {
        return pointNearestCircle.distance(centerCircle) < INSIDE_STARTING_LOC_THRESHOLD;
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

    public PathStartingLocation addStartingLocation(double x, double y) {
        if(canCreateStartingLocationAt(x, y)){
            PathStartingLocation loc = new PathStartingLocation(x, y);
            myStartingLocations.add(loc);
            return loc;
        }
        return null;
    }

    private boolean canCreateStartingLocationAt (double x, double y) {
        return !isAnotherStartingLocationToClose(x, y) && 
                myStartingLocations.size() < MAX_NUM_STARTING_LOCS;
    }

    private boolean isAnotherStartingLocationToClose (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        return myStartingLocations.stream()
                .filter(loc->isLocationCloseTo(loc, newLocation)).count() > 0;
    }

    public PathEndingLocation addEndingLocation(double x, double y) {
        if(canCreateEndingLocationAt(x, y)){
            PathEndingLocation loc = new PathEndingLocation(x, y);
            myEndingLocations.add(loc);
            return loc;
        }
        return null;
    }

    private boolean canCreateEndingLocationAt (double x, double y) {
        return !isAnotherEndingLocationToClose(x, y) &&
                myEndingLocations.size() < MAX_NUM_ENDING_LOCS;
    }

    private boolean isAnotherEndingLocationToClose (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        return myEndingLocations.stream()
                .filter(loc->isLocationCloseTo(loc, newLocation)).count() > 0;
    }

    private boolean isLocationCloseTo (PathLocation pathLocation, Point2D newLocation) {
        return pathLocation.getCenterLoc().distance(newLocation) < MIN_DISTANCE_BTW_LOCS;
    }

    public void addEndingLocation(PathEndingLocation loc) {
        myEndingLocations.add(loc);
    }

    public boolean startingLocationsConfiguredCorrectly () {
        return !myStartingLocations.isEmpty();
    }

    public boolean endingLocationsConfiguredCorrectly () {
        return !myEndingLocations.isEmpty();
    }

    public void handleComponentSelection (PathComponent componentClickedOn) {
        if(isComponentInPreviouslySelectedComponent(componentClickedOn)){
            deselectSelectedConnectedComponent();
        }
        else{
            deselectSelectedConnectedComponent();
            mySelectedComponent = componentClickedOn;
            LinkedList<PathComponent> selectedConnectedComponent = 
                    getConnectedComponentContaining(mySelectedComponent);
            for(PathComponent comp:selectedConnectedComponent) {
                comp.select();
            }
        }
    }

    private boolean isComponentInPreviouslySelectedComponent (PathComponent componentClickedOn) {
        if(mySelectedComponent != null){
            LinkedList<PathComponent> selectedConnectedComponent = 
                    getConnectedComponentContaining(mySelectedComponent);
            return selectedConnectedComponent.stream()
                    .filter(comp->comp.equals(componentClickedOn)).count() > 0;
        }
        return false;
    }

    private void deselectSelectedConnectedComponent () {
        if(mySelectedComponent != null){
            LinkedList<PathComponent> selectedConnectedComponent = getConnectedComponentContaining(mySelectedComponent);
            for(PathComponent comp:selectedConnectedComponent) {
                comp.deselect();
            }
            mySelectedComponent = null;
        }
    }

    public List<PathComponent> deleteSelectedComponent () {
        if(mySelectedComponent != null){
            LinkedList<PathComponent> connectedComponentToDelete = 
                    getConnectedComponentContaining(mySelectedComponent);
            myPath.remove(connectedComponentToDelete);
            mySelectedComponent = null; 
            return connectedComponentToDelete;    
        }
        return null;
    }

    public List<PathComponent> getAllPathComponents(){
        List<PathComponent> componentsList = new ArrayList<PathComponent>();
        for(LinkedList<PathComponent> connectedComponent:myPath){
            componentsList.addAll(connectedComponent);
        }
        return componentsList;
    }
}

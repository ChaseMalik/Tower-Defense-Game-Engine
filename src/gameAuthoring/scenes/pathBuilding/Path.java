package gameAuthoring.scenes.pathBuilding;

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

    private List<StartingLocation> myStartingLocations;    
    private List<EndingLocation> myEndingLocations;
    private List<LinkedList<PathComponent>> myPath;

    private PathComponent mySelectedComponent;

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
                createNewConnectedComponent(componentToAdd);
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

    public StartingLocation addStartingLocation(double x, double y) {
        if(canCreateStartingLocationAt(x, y)){
            StartingLocation loc = new StartingLocation(x, y);
            myStartingLocations.add(loc);
            return loc;
        }
        return null;
    }

    private boolean canCreateStartingLocationAt (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        for(StartingLocation loc:myStartingLocations){
            Point2D centerOfStartingLoc = new Point2D(loc.getCenterX(), loc.getCenterY());
            if(centerOfStartingLoc.distance(newLocation) < MIN_DISTANCE_BTW_LOCS){
                return false;
            }
        }
        return myStartingLocations.size() < MAX_NUM_STARTING_LOCS;
    }

    public EndingLocation addEndingLocation(double x, double y) {
        if(canCreateEndingLocationAt(x, y)){
            EndingLocation loc = new EndingLocation(x, y);
            myEndingLocations.add(loc);
            return loc;
        }
        return null;
    }

    private boolean canCreateEndingLocationAt (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        for(EndingLocation loc:myEndingLocations){
            Point2D centerOfStartingLoc = new Point2D(loc.getCenterX(), loc.getCenterY());
            if(centerOfStartingLoc.distance(newLocation) < MIN_DISTANCE_BTW_LOCS){
                return false;
            }
        }
        return myEndingLocations.size() < MAX_NUM_ENDING_LOCS;
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

    public void setSelectedComponent (PathLine tempLine) {
        deselectSelectedConnectedComponent();
        mySelectedComponent = tempLine;
        LinkedList<PathComponent> selectedConnectedComponent = getConnectedComponentContaining(mySelectedComponent);
        if(selectedConnectedComponent != null){
            for(PathComponent comp:selectedConnectedComponent) {
                comp.select();
            }
        }

    }

    private void deselectSelectedConnectedComponent () {
        if(mySelectedComponent == null){
            return;
        }
        LinkedList<PathComponent> selectedConnectedComponent = getConnectedComponentContaining(mySelectedComponent);
        if(selectedConnectedComponent != null){
            for(PathComponent comp:selectedConnectedComponent) {
                comp.deselect();
            }
        }
        mySelectedComponent = null;
    }

    public LinkedList<PathComponent> deleteSelectedComponent () {
        if(mySelectedComponent == null){
            return null;
        }
        LinkedList<PathComponent> connectedComponentToDelete = getConnectedComponentContaining(mySelectedComponent);
        if(connectedComponentToDelete != null){
            myPath.remove(connectedComponentToDelete);
        }
        mySelectedComponent = null; 
        return connectedComponentToDelete;
        
    }
}

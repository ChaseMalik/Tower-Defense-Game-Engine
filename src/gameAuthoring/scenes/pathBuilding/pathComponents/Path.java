package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;

public class Path {

    private static final double CONNECT_THRESHOLD = 40;
    private static final double INSIDE_STARTING_LOC_THRESHOLD = 50;
    private static final double MIN_DISTANCE_BTW_LOCS = 150;
    private static final int MAX_NUM_STARTING_LOCS = 2;
    private static final int MAX_NUM_ENDING_LOCS = 2;

    private List<PathStartingLocation> myStartingLocations;    
    private List<PathEndingLocation> myEndingLocations;
    private List<PathRoute> myPath;

    private PathComponent mySelectedComponent;
    private static Group myGroup;

    public Path(Group group) {
        myGroup = group;
        myPath = new ArrayList<PathRoute>();
        myStartingLocations = new ArrayList<PathStartingLocation>();
        myEndingLocations = new ArrayList<PathEndingLocation>();
    }

    public void addComponentToPath(PathComponent componentToAdd) {
        createNewConnectedComponent(componentToAdd);
        if(!componentAddedToStartingLocation(componentToAdd)){
            attemptToConnectComponents(componentToAdd);
        }
    }

    private boolean componentAddedToStartingLocation (PathComponent componentToAdd) {
        for(PathStartingLocation startingLoc:myStartingLocations){
            Point2D centerOfStartingLoc = 
                    new Point2D(startingLoc.getCenterX(), startingLoc.getCenterY());
            if(addedComponentIsWithinCircle(componentToAdd.getStartingPoint(), centerOfStartingLoc)) {
                componentToAdd.setStartingPoint(centerOfStartingLoc);
                getConnectedComponentContaining(componentToAdd).setStartingLocation(startingLoc);
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
                getConnectedComponentContaining(componentToAdd).setEndingLocation(endingLoc);               
                return true;
            }
        }
        return false;
    }

    public boolean attemptToConnectComponents (PathComponent comp) {
        PathRoute connectedComponent1 = 
                getConnectedComponentContaining(comp);        
        for(PathRoute connectedComponent2:myPath){
            if(!connectedComponent1.equals(connectedComponent2)){
                for(PathComponent component:connectedComponent2) {
                    if(!component.equals(connectedComponent2.getLast())){
                        if(closeEnoughToConnect(component, connectedComponent1.getFirst())){
                            PathRoute componentsBefore = connectedComponent2.getComponentsBefore(component);
                            //Have to add new component.
                            drawComponents(componentsBefore.getComponents());
                            myPath.add(componentsBefore);
                            connectComponents(componentsBefore, connectedComponent1);
                            return true;
                        }
                    }
                    
                                        
                    //Connecting component 1 to the end of component 2
                    //or connecting component 2 to the end of component 1
                    else {
                        if(closeEnoughToConnect(connectedComponent1.getLast(), connectedComponent2.getFirst())) {
                            connectComponents(connectedComponent1, connectedComponent2);
                            return true;
                        }
                        else if(closeEnoughToConnect(connectedComponent2.getLast(), connectedComponent1.getFirst())){
                            connectComponents(connectedComponent2, connectedComponent1);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void drawComponents (List<PathComponent> components) {
        for(PathComponent component:components){
            myGroup.getChildren().add(0, (Node) component);
        }
        
    }

    private void connectComponents (PathRoute connectedComponent1,
                                    PathRoute connectedComponent2) {
        connectedComponent2.getFirst().setStartingPoint(connectedComponent1.getLast().getEndingPoint());
        connectedComponent1.addAll(connectedComponent2);
        myPath.remove(connectedComponent2);
    }

    private boolean addedComponentIsWithinCircle (Point2D pointNearestCircle, Point2D centerCircle) {
        return pointNearestCircle.distance(centerCircle) < INSIDE_STARTING_LOC_THRESHOLD;
    }

    private void createNewConnectedComponent (PathComponent componentToAdd) {
        PathRoute newConnectedComponent = new PathRoute();
        newConnectedComponent.add(componentToAdd);
        myPath.add(newConnectedComponent);
    }

    private boolean closeEnoughToConnect (PathComponent last, PathComponent componentToAdd) {
        return last.getEndingPoint().distance(componentToAdd.getStartingPoint()) < CONNECT_THRESHOLD;
    }

    public void moveConnectedComponent (PathComponent draggedComponent, double deltaX, double deltaY) {
        PathRoute connectedComponent = 
                getConnectedComponentContaining(draggedComponent);
        if(connectedComponent.isNotConnectedToStartOrEndLocations()){
            for(PathComponent component:connectedComponent) {
                component.translate(deltaX, deltaY);
            }
        }
    }

    private PathRoute getConnectedComponentContaining (PathComponent comp) {
        for(PathRoute connectedComponent:myPath){
            for(PathComponent component:connectedComponent) {
                if(comp.equals(component)) {
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
            PathRoute selectedConnectedComponent = 
                    getConnectedComponentContaining(mySelectedComponent);
            for(PathComponent comp:selectedConnectedComponent) {
                bringComponentToFrontOfGroup(comp);
                comp.select();
            }
        }
    }

    private void bringComponentToFrontOfGroup (PathComponent comp) {
        myGroup.getChildren().remove(comp);
        myGroup.getChildren().add((Node) comp);
        
    }

    private boolean isComponentInPreviouslySelectedComponent (PathComponent componentClickedOn) {
        if(mySelectedComponent != null){
            PathRoute selectedConnectedComponent = 
                    getConnectedComponentContaining(mySelectedComponent);
            return selectedConnectedComponent.getComponents().stream()
                    .filter(comp->comp.equals(componentClickedOn)).count() > 0;
        }
        return false;
    }

    private void deselectSelectedConnectedComponent () {
        if(mySelectedComponent != null){
            PathRoute selectedConnectedComponent = getConnectedComponentContaining(mySelectedComponent);
            for(PathComponent comp:selectedConnectedComponent) {
                comp.deselect();
            }
            mySelectedComponent = null;
        }
    }

    public List<PathComponent> deleteSelectedComponent () {
        if(mySelectedComponent != null){
            PathRoute connectedComponentToDelete = 
                    getConnectedComponentContaining(mySelectedComponent);
            myPath.remove(connectedComponentToDelete);
            mySelectedComponent = null; 
            return connectedComponentToDelete.getComponents();    
        }
        return null;
    }

    public List<PathComponent> getAllPathComponents(){
        List<PathComponent> componentsList = new ArrayList<PathComponent>();
        for(PathRoute connectedComponent:myPath){
            componentsList.addAll(connectedComponent.getComponents());
        }
        return componentsList;
    }
}

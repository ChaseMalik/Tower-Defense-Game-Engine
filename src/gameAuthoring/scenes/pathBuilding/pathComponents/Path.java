package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;


/**
 * Holds all of the routes and all of the logic to connect disconnected routes together,
 * to add new routes, and to create forks.
 * 
 * @author Austin Kyker
 *
 */
public class Path implements Iterable<PathRoute> {

    protected static final Color VISIBLE_COLOR = Color.BLUE;
    protected static final Color INVISIBLE_COLOR = new Color(0, 0, 0, .3);

    private static final double CONNECT_THRESHOLD = 40;
    private static final double INSIDE_STARTING_LOC_THRESHOLD = 50;
    private static final double MIN_DISTANCE_BTW_LOCS = 150;

    private List<PathStartingLocation> myStartingLocations;
    private List<PathEndingLocation> myEndingLocations;
    private List<PathRoute> myPath;

    private PathComponent mySelectedComponent;
    private static Group myGroup;

    public Path (Group group) {
        myGroup = group;
        myPath = new ArrayList<PathRoute>();
        myStartingLocations = new ArrayList<PathStartingLocation>();
        myEndingLocations = new ArrayList<PathEndingLocation>();
    }

    /**
     * First tries to add a component to a starting location and if this fails,
     * the method tries to add the component to another pre-existing route. Otherwise
     * it starts its own new route.
     * 
     * @param componentToAdd
     */
    public void addComponentToPath (PathComponent componentToAdd) {
        createNewRoute(componentToAdd);
        if (!componentAddedToStartingLocation(componentToAdd)) {
            attemptToConnectRoutes(componentToAdd);
        }
    }

    /**
     * Checks to see if a starting location is close to the starting point of the component.
     * If so, it connects the component to the starting point.
     */
    private boolean componentAddedToStartingLocation (PathComponent componentToAdd) {
        for (PathStartingLocation startingLoc : myStartingLocations) {
            Point2D centerOfStartingLoc =
                    new Point2D(startingLoc.getCenterX(), startingLoc.getCenterY());
            if (addedComponentIsWithinCircle(componentToAdd.getStartingPoint(), centerOfStartingLoc)) {
                componentToAdd.setStartingPoint(centerOfStartingLoc);
                getRouteContaining(componentToAdd).setStartingLocation(startingLoc);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the ending point of the component added is close to an ending location.
     * If so, the component is connected to the end point.
     */
    public boolean tryToAddConnectComponentToEndingLocation (PathComponent componentToAdd) {
        for (PathEndingLocation endingLoc : myEndingLocations) {
            Point2D centerCircle = new Point2D(endingLoc.getCenterX(), endingLoc.getCenterY());
            if (addedComponentIsWithinCircle(componentToAdd.getEndingPoint(), centerCircle)) {
                componentToAdd.setEndingPoint(centerCircle);
                getRouteContaining(componentToAdd).setEndingLocation(endingLoc);
                return true;
            }
        }
        return false;
    }

    /**
     * Tries to connect two routes together. Not only checks to see if the end of one route
     * can be connected to the beginning of another route and vice versa, but also checks to
     * see if a fork can be made. In the case of the fork the components in the pre-existing route
     * above the fork are copied and added to the head of the added component. This way two routes
     * are generated and they each have seperate components rather than sharing components.
     */
    public boolean attemptToConnectRoutes (PathComponent comp) {
        PathRoute connectedComponent1 =
                getRouteContaining(comp);
        for (PathRoute connectedComponent2 : myPath) {
            if (!connectedComponent1.equals(connectedComponent2)) {
                for (PathComponent component : connectedComponent2) {
                    if (!component.equals(connectedComponent2.getLast())) {
                        if (closeEnoughToConnect(component, connectedComponent1.getFirst())) {
                            PathRoute componentsBefore =
                                    connectedComponent2.getComponentsBefore(component);
                            // Have to add new component.
                            drawComponents(componentsBefore.getComponents());
                            myPath.add(componentsBefore);
                            connectRoutes(componentsBefore, connectedComponent1);
                            ensureLocationsAreInFront();
                            return true;
                        }
                    }

                    // Connecting component 1 to the end of component 2
                    // or connecting component 2 to the end of component 1
                    else {
                        if (closeEnoughToConnect(connectedComponent1.getLast(),
                                                 connectedComponent2.getFirst())) {
                            connectRoutes(connectedComponent1, connectedComponent2);
                            return true;
                        }
                        else if (closeEnoughToConnect(connectedComponent2.getLast(),
                                                      connectedComponent1.getFirst())) {
                            connectRoutes(connectedComponent2, connectedComponent1);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void drawComponents (List<PathComponent> components) {
        for (PathComponent component : components) {
            myGroup.getChildren().add((Node) component);
        }
    }

    /**
     * Simply connects the ending point of connectedComponent1 to the starting point of
     * connectedComponent2
     * 
     * @param connectedComponent1
     * @param connectedComponent2
     */
    protected void connectRoutes (PathRoute connectedComponent1,
                                  PathRoute connectedComponent2) {
        connectedComponent2.getFirst().setStartingPoint(connectedComponent1.getLast()
                                                                .getEndingPoint());
        connectedComponent1.addAll(connectedComponent2);
        myPath.remove(connectedComponent2);
    }

    private boolean addedComponentIsWithinCircle (Point2D pointNearestCircle, Point2D centerCircle) {
        return pointNearestCircle.distance(centerCircle) < INSIDE_STARTING_LOC_THRESHOLD;
    }

    private void createNewRoute (PathComponent componentToAdd) {
        PathRoute newConnectedComponent = new PathRoute();
        newConnectedComponent.add(componentToAdd);
        myPath.add(newConnectedComponent);
    }

    private boolean closeEnoughToConnect (PathComponent last, PathComponent componentToAdd) {
        return last.getEndingPoint().distance(componentToAdd.getStartingPoint()) < CONNECT_THRESHOLD;
    }

    /**
     * Moves the entire route. This function is called in selection mode when the user picks up
     * a route and drags it across screen.
     */
    public void moveConnectedComponent (PathComponent draggedComponent, double deltaX, double deltaY) {
        PathRoute connectedComponent =
                getRouteContaining(draggedComponent);
        if (connectedComponent.isNotConnectedToStartOrEndLocations()) {
            for (PathComponent component : connectedComponent) {
                component.translate(deltaX, deltaY);
            }
        }
    }

    protected PathRoute getRouteContaining (PathComponent comp) {
        for (PathRoute connectedComponent : myPath) {
            for (PathComponent component : connectedComponent) {
                if (comp.equals(component)) { return connectedComponent; }
            }
        }
        return null;
    }

    public PathStartingLocation addStartingLocation (double x, double y) {
        if (canCreateLocationAtPoint(x, y)) {
            PathStartingLocation loc = new PathStartingLocation(x, y);
            myStartingLocations.add(loc);
            return loc;
        }
        return null;
    }

    private boolean isAnotherStartingLocationToClose (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        return myStartingLocations.stream()
                .filter(loc -> isLocationCloseToPoint(loc, newLocation)).count() > 0;
    }

    public PathEndingLocation addEndingLocation (double x, double y) {
        if (canCreateLocationAtPoint(x, y)) {
            PathEndingLocation loc = new PathEndingLocation(x, y);
            myEndingLocations.add(loc);
            return loc;
        }
        return null;
    }

    protected boolean canCreateLocationAtPoint (double x, double y) {
        return !isAnotherEndingLocationToClose(x, y) &&
               !isAnotherStartingLocationToClose(x, y);
    }

    private boolean isAnotherEndingLocationToClose (double x, double y) {
        Point2D newLocation = new Point2D(x, y);
        return myEndingLocations.stream()
                .filter(loc -> isLocationCloseToPoint(loc, newLocation)).count() > 0;
    }

    protected boolean isLocationCloseToPoint (PathLocation pathLocation, Point2D newLocation) {
        return pathLocation.getCenterLoc().distance(newLocation) < MIN_DISTANCE_BTW_LOCS;
    }

    public boolean startingLocationsConfiguredCorrectly () {
        return !myStartingLocations.isEmpty();
    }

    public boolean endingLocationsConfiguredCorrectly () {
        return !myEndingLocations.isEmpty();
    }

    /**
     * Handles the selection of components in selection mode. Ensures that the route
     * that is clicked on is selected (green). If it was already selected, then it is
     * deselected.
     */
    public void handleComponentSelection (PathComponent componentClickedOn) {
        if (isComponentInPreviouslySelectedComponent(componentClickedOn)) {
            deselectSelectedConnectedComponent();
        }
        else {
            deselectSelectedConnectedComponent();
            mySelectedComponent = componentClickedOn;
            PathRoute selectedConnectedComponent =
                    getRouteContaining(mySelectedComponent);
            for (PathComponent comp : selectedConnectedComponent) {
                bringComponentToFrontOfGroup((Node) comp);
                comp.select();
            }
            ensureLocationsAreInFront();
        }
    }

    /**
     * Ensures that the starting and ending location nodes are in front
     * of the path component nodes to make it more aesthetically pleasing.
     */
    private void ensureLocationsAreInFront () {
        for (PathStartingLocation loc : myStartingLocations) {
            bringComponentToFrontOfGroup(loc);
        }
        for (PathEndingLocation loc : myEndingLocations) {
            bringComponentToFrontOfGroup(loc);
        }
    }

    private void bringComponentToFrontOfGroup (Node comp) {
        myGroup.getChildren().remove(comp);
        myGroup.getChildren().add((Node) comp);
    }

    private boolean isComponentInPreviouslySelectedComponent (PathComponent componentClickedOn) {
        if (mySelectedComponent != null) {
            PathRoute selectedConnectedComponent =
                    getRouteContaining(mySelectedComponent);
            return selectedConnectedComponent.getComponents().stream()
                    .filter(comp -> comp.equals(componentClickedOn)).count() > 0;
        }
        return false;
    }

    private void deselectSelectedConnectedComponent () {
        if (mySelectedComponent != null) {
            PathRoute selectedConnectedComponent = getRouteContaining(mySelectedComponent);
            for (PathComponent comp : selectedConnectedComponent) {
                comp.removeStroke();
            }
            mySelectedComponent = null;
        }
    }

    public List<PathComponent> deleteSelectedComponent () {
        if (mySelectedComponent != null) {
            PathRoute connectedComponentToDelete =
                    getRouteContaining(mySelectedComponent);
            myPath.remove(connectedComponentToDelete);
            mySelectedComponent = null;
            return connectedComponentToDelete.getComponents();
        }
        return null;
    }

    public List<PathComponent> getAllPathComponents () {
        List<PathComponent> componentsList = new ArrayList<PathComponent>();
        for (PathRoute connectedComponent : myPath) {
            componentsList.addAll(connectedComponent.getComponents());
        }
        return componentsList;
    }

    public void clearEnemyStartingLocations () {
        myGroup.getChildren().removeAll(myStartingLocations);
        myStartingLocations.clear();

    }

    public void clearEnemyEndingLocations () {
        myGroup.getChildren().removeAll(myEndingLocations);
        myEndingLocations.clear();
    }

    protected int getNumRoutes () {
        return myPath.size();
    }

    // method is only here for CreationTests. Classes outside
    // the path cannot use it.
    protected List<PathRoute> getRoutes () {
        return myPath;
    }

    @Override
    public Iterator<PathRoute> iterator () {
        return myPath.iterator();
    }

    // All routes must be completed...They must be connected to a start and ending location
    public boolean isCompletedAndRoutesVerified () {
        if (myPath.isEmpty()) return false;
        for (PathRoute route : myPath) {
            if (!route.isConnectedToStartAndEndLocations()) { return false; }
        }
        return true;
    }

    public void resetPath () {
        myPath.clear();
        myGroup.getChildren().clear();
        clearEnemyEndingLocations();
        clearEnemyStartingLocations();
    }

    public boolean intersects (Tile tile) {
        return this.getAllPathComponents().stream()
                .filter(comp -> comp.getNode().intersects(tile.getBoundsInLocal()))
                .count() > 0;
    }
}

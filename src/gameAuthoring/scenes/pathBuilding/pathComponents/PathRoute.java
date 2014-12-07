package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Defines a route that has a starting location and an ending location as well
 * as a list of path components that connect these locations.
 * 
 * @author Austin Kyker
 *
 */
public class PathRoute implements Iterable<PathComponent> {
    private PathStartingLocation myStartingLocation;
    private PathEndingLocation myEndingLocation;

    private List<PathComponent> myConnectedComponent;

    public PathRoute () {
        myConnectedComponent = new ArrayList<PathComponent>();
    }

    @Override
    public Iterator<PathComponent> iterator () {
        return myConnectedComponent.iterator();
    }

    public void setStartingLocation (PathStartingLocation loc) {
        myStartingLocation = loc;
    }

    public void setEndingLocation (PathEndingLocation loc) {
        myEndingLocation = loc;
    }

    public void add (PathComponent componentToAdd) {
        myConnectedComponent.add(componentToAdd);
    }

    public PathComponent getFirst () {
        return myConnectedComponent.get(0);
    }

    public PathComponent getLast () {
        return myConnectedComponent.get(myConnectedComponent.size() - 1);
    }

    public void addAll (PathRoute connectedComponent) {
        for (PathComponent comp : connectedComponent) {
            myConnectedComponent.add(comp);
        }
    }

    public List<PathComponent> getComponents () {
        return myConnectedComponent;
    }

    public boolean isNotConnectedToStartOrEndLocations () {
        return myStartingLocation == null && myEndingLocation == null;
    }

    public boolean isConnectedToStartAndEndLocations () {
        return myStartingLocation != null && myEndingLocation != null;
    }

    public boolean isConnectedToStartingLocation () {
        return myStartingLocation != null;
    }

    public boolean isConnectToEndingLocation () {
        return myEndingLocation != null;
    }

    public PathRoute getComponentsBefore (PathComponent component) {
        PathRoute components = new PathRoute();
        components.setStartingLocation(myStartingLocation);
        for (int i = 0; i < myConnectedComponent.size(); i++) {
            PathComponent comp = myConnectedComponent.get(i);
            components.add(myConnectedComponent.get(i).deepCopy());
            if (comp.equals(component)) {
                break;
            }
        }
        return components;
    }
}

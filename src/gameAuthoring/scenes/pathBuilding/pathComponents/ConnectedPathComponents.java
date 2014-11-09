package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.enemyLocations.PathEndingLocation;
import gameAuthoring.scenes.pathBuilding.enemyLocations.PathStartingLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectedPathComponents implements Iterable<PathComponent> {
    private PathStartingLocation myStartingLocation;
    private PathEndingLocation myEndingLocation;
    
    private List<PathComponent> myConnectedComponent;
    
    public ConnectedPathComponents() {
        myConnectedComponent = new ArrayList<PathComponent>();
    }

    @Override
    public Iterator<PathComponent> iterator () {
        return myConnectedComponent.iterator();
    }
    
    public void setStartingLocation(PathStartingLocation loc) {
        myStartingLocation = loc;
    }
    
    public void setEndingLocation(PathEndingLocation loc) {
        myEndingLocation = loc;
    }

    public void add (PathComponent componentToAdd) {
        myConnectedComponent.add(componentToAdd);
    }
    
    public PathComponent getFirst() {
        return myConnectedComponent.get(0);
    }
    
    public PathComponent getLast() {
        return myConnectedComponent.get(myConnectedComponent.size()-1);
    }

    public void addAll (ConnectedPathComponents connectedComponent) {
        for(PathComponent comp:connectedComponent){
            myConnectedComponent.add(comp);
        }       
    }

    public List<PathComponent> getComponents () {
        return myConnectedComponent;
    }

    public boolean isNotConnectedToStartOrEndLocations () {
        return myStartingLocation == null && myEndingLocation == null;
    }
   
}

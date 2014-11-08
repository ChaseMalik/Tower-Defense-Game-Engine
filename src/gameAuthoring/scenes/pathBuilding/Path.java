package gameAuthoring.scenes.pathBuilding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private static final double CONNECT_THRESHOLD = 30;
    
    private List<LinkedList<PathComponent>> myPath;
    
    public Path() {
        myPath = new ArrayList<LinkedList<PathComponent>>();
    }
    
    public void addPathComponentToPath(PathComponent componentToAdd) {
        if(!componentSuccessfullyAddedToEndOfAConnectedComponent(componentToAdd)) {
            createNewConnectedComponent(componentToAdd);
        }
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
}

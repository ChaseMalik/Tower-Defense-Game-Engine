package gameAuthoring.scenes.pathBuilding.routeToPointTranslation;

import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathRoute;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a back-end representation of a single enemy route. The points
 * specify how the enemy will traverse the screen from the starting location
 * to the ending location. The first point represents the starting location
 * and the last point represents the ending locations. The visibility of the
 * visibility point determines if the enemy will be visible or invisible while
 * it heads to the next point in the route.
 * @author Austin Kyker
 *
 */
public class BackendRoute implements Iterable<VisibilityPoint> {
    private List<VisibilityPoint> myPoints;
    
    public BackendRoute(PathRoute route){
        setUpBackendRouteFromFrontEndRoute(route);
    }

    private void setUpBackendRouteFromFrontEndRoute (PathRoute route) {
        for(PathComponent component:route) {
            myPoints.add(new VisibilityPoint(true, component.getStartingPoint()));
        }       
        myPoints.add(new VisibilityPoint(true, route.getLast().getEndingPoint()));
    }

    @Override
    public Iterator<VisibilityPoint> iterator () {
        return myPoints.iterator();
    }

}

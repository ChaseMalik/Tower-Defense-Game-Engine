package gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation;

import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathRoute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;

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
public class BackendRoute {
    private List<VisibilityPoint> myPoints;
    
    //Used for non-path TD games and also on the backend to calculate
    //the routes of the bullets.
    public BackendRoute(Point2D start, Point2D end) {
        myPoints.add(new VisibilityPoint(true, start));
        myPoints.add(new VisibilityPoint(true, end));
    }
    
    public BackendRoute(PathRoute route){
        setUpBackendRouteFromFrontEndRoute(route);
    }

    private void setUpBackendRouteFromFrontEndRoute (PathRoute route) {
        myPoints = new ArrayList<VisibilityPoint>();
        for(PathComponent component:route) {
            myPoints.add(new VisibilityPoint(true, component.getStartingPoint()));
        }       
        myPoints.add(new VisibilityPoint(true, route.getLast().getEndingPoint()));
    }

    public List<VisibilityPoint> getPoints() {
        return Collections.unmodifiableList(myPoints);
    }
}

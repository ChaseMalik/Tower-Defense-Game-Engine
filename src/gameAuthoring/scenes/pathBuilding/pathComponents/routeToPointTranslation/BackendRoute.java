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
 * 
 * @author Austin Kyker
 *
 */
public class BackendRoute {
    private List<VisibilityPoint> myPoints;

    public BackendRoute () {
        myPoints = new ArrayList<VisibilityPoint>();
    }

    // Used for non-path TD games and also on the back-end to calculate
    // the routes of the bullets.
    public BackendRoute (Point2D start, Point2D end) {
        this();
        myPoints.add(new VisibilityPoint(true, start));
        myPoints.add(new VisibilityPoint(true, end));
    }

    public BackendRoute (PathRoute route) {
        this();
        setUpBackendRouteFromFrontEndRoute(route);
    }

    private void setUpBackendRouteFromFrontEndRoute (PathRoute route) {
        for (PathComponent component : route) {
            myPoints.add(new VisibilityPoint(component.isPathComponentVisible(), component
                    .getStartingPoint()));
            myPoints.addAll(component.getInnerPointsRepresentingComponent());
        }
        myPoints.add(new VisibilityPoint(true, route.getLast().getEndingPoint()));
    }

    public List<VisibilityPoint> getPoints () {
        return Collections.unmodifiableList(myPoints);
    }

    public BackendRoute deepCopy () {
        BackendRoute copy = new BackendRoute();
        for (int i = 0; i < myPoints.size(); i++) {
            Point2D point =
                    new Point2D(myPoints.get(i).getPoint().getX(), myPoints.get(i).getPoint()
                            .getY());
            copy.myPoints.add(new VisibilityPoint(true, point));
        }
        return copy;
    }
}

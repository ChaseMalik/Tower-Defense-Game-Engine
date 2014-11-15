package gameAuthoring.scenes.pathBuilding.routeToPointTranslation;

import javafx.geometry.Point2D;

/**
 * A point on the route from the starting location to the ending location
 * for an enemy. The visibility boolean determines if the enemy will be 
 * visible or invisible while traveling to the next point in the 
 * BackendRoute object.
 * @author Austin Kyker
 *
 */
public class VisibilityPoint {
    public boolean myVisibility;
    public Point2D myPoint;
    
    public VisibilityPoint(boolean visibility, Point2D point) {
        myVisibility = visibility;
        myPoint = point;
    }
    
    public boolean isVisible() {
        return myVisibility;
    }
    
    public Point2D getPoint() {
        return myPoint;
    }
}

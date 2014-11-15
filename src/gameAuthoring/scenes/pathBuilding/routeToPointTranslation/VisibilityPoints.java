package gameAuthoring.scenes.pathBuilding.routeToPointTranslation;

import javafx.geometry.Point2D;

public class VisibilityPoints {
    public boolean myVisibility;
    public Point2D myPoint;
    
    public VisibilityPoints(boolean visibility, Point2D point) {
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

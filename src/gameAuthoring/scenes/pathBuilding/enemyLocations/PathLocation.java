package gameAuthoring.scenes.pathBuilding.enemyLocations;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PathLocation extends Circle {
    
    private static final int RADIUS = 40;
    
    public PathLocation(double x, double y, Color color) {
        super(RADIUS);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(color);
    }
    
    public Point2D getCenterLoc() {
        return new Point2D(this.getCenterX(), this.getCenterY());
    }
}

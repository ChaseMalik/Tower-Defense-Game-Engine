package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Defines a line that can be drawn on the screen.
 * @author Austin Kyker
 *
 */
public class PathLine extends Line implements PathComponent {
    
    public PathLine() {
        this(0, 0);
    }
    
    public PathLine(double initalMouseClickX, double initialMouseClickY) {
        this.setStartX(initalMouseClickX);
        this.setStartY(initialMouseClickY);
        this.setEndX(initalMouseClickX);
        this.setEndY(initialMouseClickY);
        this.setStrokeWidth(20);
    }

    @Override
    public Point2D getStartingPoint () {
        return new Point2D(this.getStartX(), this.getStartY());
    }

    @Override
    public Point2D getEndingPoint () {
        return new Point2D(this.getEndX(), this.getEndY());
    }

    @Override
    public void setStartingPoint (Point2D startingPoint) {
        this.setStartX(startingPoint.getX());
        this.setStartY(startingPoint.getY());
    }

    @Override
    public void translate (double deltaX, double deltaY) {
        this.setStartX(this.getStartX()+deltaX);
        this.setStartY(this.getStartY()+deltaY);
        this.setEndX(this.getEndX()+deltaX);
        this.setEndY(this.getEndY()+deltaY);
    }

    @Override
    public void setEndingPoint (Point2D endingPoint) {
        this.setEndX(endingPoint.getX());
        this.setEndY(endingPoint.getY());
    }

    public double getLength () {
        return getEndingPoint().distance(getStartingPoint());
    }

    @Override
    public void select () {
        super.setStroke(Color.GREEN);        
    }
    
    @Override
    public void deselect () {
        super.setStroke(Color.BLACK);        
    }

    @Override
    public PathComponent deepCopy () {
        PathLine copy = new PathLine();
        copy.setStartingPoint(this.getStartingPoint());
        copy.setEndingPoint(this.getEndingPoint());
        copy.setStroke(this.getStroke());
        copy.setStrokeWidth(this.getStrokeWidth());
        return copy;
    }

    /**
     * When converting a JavaFX line to a set of points for an enemy to follow,
     * you simply need to include the starting and ending points and no "inner"
     * points. The enemy will simply travel from the starting location to ending
     * location on a linear path.
     */
    @Override
    public List<VisibilityPoint> getInnerPointsRepresentingComponent () {
        return new ArrayList<VisibilityPoint>();
    }

    @Override
    public List<Node> getExtraNodes () {
        return new ArrayList<Node>();
    }
}

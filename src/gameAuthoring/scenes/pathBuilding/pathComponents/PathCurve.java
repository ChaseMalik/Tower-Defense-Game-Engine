package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

/**
 * Defines a curve that can be drawn on the screen.
 * @author Austin Kyker
 *
 */
public class PathCurve extends CubicCurve implements PathComponent {

    private static final double NUM_INNER_POINTS = 15.0;
    private boolean isEndPointSet;
    private Circle myControlPoint1Setter;
    private Circle myControlPoint2Setter;
    
    public PathCurve() {
        this.setStrokeWidth(20);
        this.setStroke(Color.BLACK);
        this.setFill(null);
    }

    public PathCurve(double initialX, double initialY) {
        this();
        this.setStartingPoint(new Point2D(initialX, initialY));
        this.setEndingPoint(new Point2D(initialX, initialY));
        isEndPointSet = false;
        calculateAndSetControlPoints();
    }

    public boolean isEndPointSet() {
        return isEndPointSet;
    }

    public void endPointIsSet() {
        isEndPointSet = true;
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
    public void setEndingPoint (Point2D endingPoint) {
        this.setEndX(endingPoint.getX());
        this.setEndY(endingPoint.getY());
    }

    @Override
    public void translate (double deltaX, double deltaY) {
        this.setTranslateX(deltaX);
        this.setTranslateY(deltaY);      
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
        PathCurve copy = new PathCurve();
        copy.setStartingPoint(this.getStartingPoint());
        copy.setEndingPoint(this.getEndingPoint());
        copy.setControlPoint1(this.getControlX1(), this.getControlY1());
        copy.setControlPoint2(this.getControlX2(), this.getControlY2());
        return copy;
    }
    
    public void setControlPointSetters(Circle setter1, Circle setter2) {
        myControlPoint1Setter = setter1;
        myControlPoint2Setter = setter2;
    }

    public double getLength () {
        return getEndingPoint().distance(getStartingPoint());
    }

    public void setControlPoint1 (double x, double y) {
        this.setControlX1(x);
        this.setControlY1(y);      
    }

    public void setControlPoint2 (double x, double y){ 
        this.setControlX2(x);
        this.setControlY2(y);      
    }

    public void calculateAndSetControlPoints() {
        Point2D curveMidPoint = this.getStartingPoint().midpoint(this.getEndingPoint());
        Point2D controlPoint1Loc = this.getStartingPoint().midpoint(curveMidPoint);
        this.setControlPoint1(controlPoint1Loc.getX(), controlPoint1Loc.getY());
        Point2D controlPoint2Loc = curveMidPoint.midpoint(this.getEndingPoint());
        this.setControlPoint2(controlPoint2Loc.getX(), controlPoint2Loc.getY());        
    }

    private Point2D getControlPoint1() {
        return new Point2D(this.getControlX1(), this.getControlY1());
    }

    private Point2D getControlPoint2() {
        return new Point2D(this.getControlX2(), this.getControlY2());
    }

    /**
     * Uses the formula for the Bezier Curve to calculate several points on the curve.
     * The back-end route needs these points in order to route the enemy from the starting
     * location to the ending location.
     */
    @Override
    public List<VisibilityPoint> getInnerPointsRepresentingComponent () {
        List<VisibilityPoint> innerPoints = new ArrayList<VisibilityPoint>();
        for(double t = 1/NUM_INNER_POINTS; t < 1; t = t + 1/NUM_INNER_POINTS) {
            innerPoints.add(new VisibilityPoint(true,
                                                this.getStartingPoint().multiply(Math.pow((1-t),3))
                                                .add(getControlPoint1().multiply(3*Math.pow(1-t, 2)*t))
                                                .add(getControlPoint2().multiply(3*(1-t)*Math.pow(t,2)))
                                                .add(this.getEndingPoint().multiply(Math.pow(t, 3)))
                    ));
        }
        return innerPoints;
    }

    /**
     * When this component gets deleted, the control point setters are deleted as well from
     * the group.
     */
    @Override
    public Node[] getCorrespondingNodesToDelete () {
        return new Node[] { this, myControlPoint1Setter, myControlPoint2Setter };
    }
}

package gameAuthoring.scenes.pathBuilding.pathComponents;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class PathCurve extends CubicCurve implements PathComponent {

    private boolean isEndPointSet;
    private boolean isControlPoint1Set;
    private boolean isControlPoint2Set;

    public PathCurve(double initialX, double initialY) {
        this.setStartingPoint(new Point2D(initialX, initialY));
        this.setEndingPoint(new Point2D(initialX, initialY));
        isEndPointSet = false;
        isControlPoint1Set = false;
        isControlPoint2Set = false;
        calculateAndSetControlPoints();
        this.setStrokeWidth(20);
        this.setStroke(Color.BLACK);
        this.setFill(null);
    }

    public boolean isEndPointSet() {
        return isEndPointSet;
    }

    public void endPointIsSet() {
        isEndPointSet = true;
    }


    public boolean isControlPoint1Set() {
        return isControlPoint1Set;
    }

    public boolean isControlPoint2Set() {
        return isControlPoint2Set;
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
        // TODO Auto-generated method stub
        return null;
    }


    public double getLength () {
        return getEndingPoint().distance(getStartingPoint());
    }


    public boolean isNoControlPointSet () {
        return !(isControlPoint1Set() || isControlPoint2Set());
    }

    public void finalizeControlPoint1(double x, double y){
        setControlPoint1(x, y);
        this.isControlPoint1Set = true;
    }
    
    public void finalizeControlPoint2(double x, double y){
        setControlPoint2(x, y);
        this.isControlPoint2Set = true;
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
}

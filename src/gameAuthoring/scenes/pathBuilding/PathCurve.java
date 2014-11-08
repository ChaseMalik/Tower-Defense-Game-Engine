package gameAuthoring.scenes.pathBuilding;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurve;

public class PathCurve extends CubicCurve implements PathComponent {

    private boolean isEndPointSet;
    private boolean isControlPoint1Set;
    private boolean isControlPoint2Set;
    
    public PathCurve(double initialX, double initialY) {
        this.setStartX(initialX);
        this.setStartY(initialY);
        isEndPointSet = false;
        isControlPoint1Set = false;
        isControlPoint2Set = false;
    }
    
//    protected boolean setEndPoint(double x, double y) {
//        
//    }

    protected boolean isEndPointSet() {
        return isEndPointSet;
    }
   
    protected void setControlPoint1(double x, double y) {
        
    }

    protected boolean isControlPoint1Set() {
        return isControlPoint1Set;
    }

    protected boolean isControlPoint2Set() {
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
    public void translate (double deltaX, double deltaY) {
        this.setTranslateX(deltaX);
        this.setTranslateY(deltaY);      
    }
}

package gameAuthoring.scenes.pathBuilding.buildingPanes;

import java.util.ArrayList;
import java.util.List;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathCurve;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Defines the pane where the user can draw curves and the click-listener
 * to actually create these curves. Click once to define the starting location of the curve,
 * twice to define the ending location of the curve, and twice more to define the control points.
 * @author Austin Kyker
 *
 */
public class CurveDrawingPane extends BuildingPane {

    private static final int CONTROL_POINT_SETTER_RADIUS = 8;
    private static final int MIN_CURVE_LENGTH = 60;
    private PathCurve myCurveBeingCreated;
    private Path myPath;
    private Group myGroup;
    private List<Circle> myControlPointSetters;

    public CurveDrawingPane(Group group, Path path){
        super(group);
        myPath = path; 
        myGroup = group;
        myControlPointSetters = new ArrayList<Circle>();
        addListeners();
    }

    private void addListeners () {
        this.setOnMousePressed(event->handleBuildScreenClick(event));
        this.setOnMouseMoved(event->stretchLineToEndAtCurrentMousePosition(event));
    }

    private void stretchLineToEndAtCurrentMousePosition (MouseEvent event) {
        if(myCurveBeingCreated != null){
            myCurveBeingCreated.setEndX(event.getX());
            myCurveBeingCreated.setEndY(event.getY());
            myCurveBeingCreated.calculateAndSetControlPoints();
        }
    }   

    private void handleBuildScreenClick(MouseEvent event) {
        if(!clickedOnControlPoint(event)){
            if(myCurveBeingCreated == null){
                myCurveBeingCreated = new PathCurve(event.getX(), event.getY());
                drawPathComponent(myCurveBeingCreated);
                myPath.addComponentToPath(myCurveBeingCreated);
                addListeners();
            }
            else if(!myCurveBeingCreated.isEndPointSet()) {
                if(myCurveBeingCreated.getLength() > MIN_CURVE_LENGTH){
                    myPath.tryToAddConnectComponentToEndingLocation(myCurveBeingCreated);
                    myCurveBeingCreated.endPointIsSet();
                    this.setOnMouseMoved(null);
                    super.removeComponentFromScreen(myCurveBeingCreated);
                    PathCurve tempCurve = myCurveBeingCreated;
                    drawPathComponent(tempCurve);
                    myPath.tryToAddConnectComponentToEndingLocation(tempCurve);
                    addControlPointSetters(tempCurve);
                    myCurveBeingCreated = null;
                }
            }
        }
    }

    private boolean clickedOnControlPoint (MouseEvent event) {
        for(Circle c:myControlPointSetters) {
            Point2D setterCenter = new Point2D(c.getCenterX(), c.getCenterY());
            Point2D clickLoc = new Point2D(event.getX(), event.getY());
            if(setterCenter.distance(clickLoc) < CONTROL_POINT_SETTER_RADIUS) {
                return true;
            }
        }
        return false;
    }

    private void addControlPointSetters (PathCurve tempCurve) {
        Circle controlPoint1Setter = getControlPointSetter(tempCurve.getControlX1(), tempCurve.getControlY1());
        Circle controlPoint2Setter = getControlPointSetter(tempCurve.getControlX2(), tempCurve.getControlY2());
        controlPoint1Setter.setOnMouseDragged(event->moveControlPoint1(tempCurve, controlPoint1Setter, event));
        controlPoint2Setter.setOnMouseDragged(event->moveControlPoint2(tempCurve, controlPoint2Setter, event));
        myGroup.getChildren().addAll(controlPoint1Setter, controlPoint2Setter);
        tempCurve.setControlPointSetters(controlPoint1Setter, controlPoint2Setter);
    }

    private void moveControlPoint1 (PathCurve tempCurve, Circle controlPoint1Setter, MouseEvent event) {
        tempCurve.setControlPoint1(event.getX(), event.getY());
        moveControlPointSetter(controlPoint1Setter, event);
    }

    private void moveControlPoint2 (PathCurve tempCurve, Circle controlPoint2Setter, MouseEvent event) {
        tempCurve.setControlPoint2(event.getX(), event.getY());
        moveControlPointSetter(controlPoint2Setter, event);
    }

    private void moveControlPointSetter (Circle controlPointSetter, MouseEvent event) {
        controlPointSetter.setCenterX(event.getX());
        controlPointSetter.setCenterY(event.getY());
    }

    private Circle getControlPointSetter (double x, double y) {
        Circle controlPointSetter = new Circle(x, y, CONTROL_POINT_SETTER_RADIUS, Color.YELLOW);
        myControlPointSetters.add(controlPointSetter);
        return controlPointSetter;
    }
    
    @Override
    public void executeEnterFunction() {
        myGroup.getChildren().addAll(myControlPointSetters);
    }
    
    @Override
    public void executeExitFunction() {
        myGroup.getChildren().removeAll(myControlPointSetters);
    }
}

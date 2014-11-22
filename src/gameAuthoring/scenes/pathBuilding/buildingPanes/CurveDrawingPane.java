package gameAuthoring.scenes.pathBuilding.buildingPanes;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathCurve;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 * Defines the pane where the user can draw curves and the click-listener
 * to actually create these curves. Click once to define the starting location of the curve,
 * twice to define the ending location of the curve, and twice more to define the control points.
 * @author Austin Kyker
 *
 */
public class CurveDrawingPane extends BuildingPane {

    private static final int MIN_CURVE_LENGTH = 60;
    private PathCurve myCurveBeingCreated;
    private Path myPath;

    public CurveDrawingPane(Group group, Path path){
        super(group);
        myPath = path;  
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
            }
        }
        else if(!myCurveBeingCreated.isControlPoint1Set()) {
            myCurveBeingCreated.finalizeControlPoint1(event.getX(), event.getY());
        }
        else if(!myCurveBeingCreated.isControlPoint2Set()) {
            myCurveBeingCreated.finalizeControlPoint2(event.getX(), event.getY());
            super.removeComponentFromScreen(myCurveBeingCreated);
            if(myCurveBeingCreated.getLength() > MIN_CURVE_LENGTH){
                PathCurve tempCurve = myCurveBeingCreated;
                drawPathComponent(tempCurve);
                myPath.tryToAddConnectComponentToEndingLocation(tempCurve);
            }
            myCurveBeingCreated = null;
        }
    }
}

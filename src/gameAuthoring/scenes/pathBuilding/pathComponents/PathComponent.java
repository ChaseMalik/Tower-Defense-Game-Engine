package gameAuthoring.scenes.pathBuilding.pathComponents;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * An interface that defines the behaviors of drawn component. Implemented
 * by PathLine and PathCurve.
 * @author Austin Kyker
 *
 */
public interface PathComponent {
    Point2D getStartingPoint();
    Point2D getEndingPoint();
    void setStartingPoint (Point2D endingPoint);
    void setEndingPoint (Point2D startingPoint);
    void translate(double deltaX, double deltaY);
    void select ();
    void deselect ();
    Node getNode();
    PathComponent deepCopy();
    List<VisibilityPoint> getInnerPointsRepresentingComponent();
    Node[] getCorrespondingNodesToDelete ();
}

package gameAuthoring.scenes.pathBuilding.pathComponents;

import javafx.geometry.Point2D;

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
    PathComponent deepCopy();
}

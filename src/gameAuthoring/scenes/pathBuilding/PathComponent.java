package gameAuthoring.scenes.pathBuilding;

import javafx.geometry.Point2D;

public interface PathComponent {
    Point2D getStartingPoint();
    Point2D getEndingPoint();
    void setStartingPoint (Point2D endingPoint);
    void setEndingPoint (Point2D startingPoint);
    void translate(double deltaX, double deltaY);
    void select ();
    void deselect ();
}

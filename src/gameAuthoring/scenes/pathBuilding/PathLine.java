package gameAuthoring.scenes.pathBuilding;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class PathLine extends Line implements PathComponent {
    public PathLine(double initalMouseClickX, double initialMouseClickY) {
        this.setStartX(initalMouseClickX);
        this.setStartY(initialMouseClickY);
        this.setEndX(initalMouseClickX);
        this.setEndY(initialMouseClickY);
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

}

package gameAuthoring.scenes.pathBuilding;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class PathLine extends Line implements PathComponent {
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

}

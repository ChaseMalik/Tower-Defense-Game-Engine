package gameAuthoring.scenes.pathBuilding;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EndingLocation extends Circle {
    public EndingLocation(double x, double y) {
        super(40);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(Color.RED);
    }
}
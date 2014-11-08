package gameAuthoring.scenes.pathBuilding;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StartingLocation extends Circle {
    public StartingLocation(double x, double y) {
        super(40);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(Color.GREEN);
    }
}

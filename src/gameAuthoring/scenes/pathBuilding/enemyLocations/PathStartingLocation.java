package gameAuthoring.scenes.pathBuilding.enemyLocations;

import javafx.scene.paint.Color;

/**
 * Defines the starting location of a possible route. This is where
 * the enemies spawn.
 * @author Austin Kyker
 *
 */
public class PathStartingLocation extends PathLocation {
    public PathStartingLocation(double x, double y) {
        super(x, y, Color.GREEN);
    }
}

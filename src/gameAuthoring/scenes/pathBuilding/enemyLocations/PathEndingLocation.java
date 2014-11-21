package gameAuthoring.scenes.pathBuilding.enemyLocations;

import javafx.scene.paint.Color;

/**
 * Defines an ending location of a possible enemy route. This is where
 * the enemies disappear and cause the player damage.
 * @author Austin Kyker
 *
 */
public class PathEndingLocation extends PathLocation {
    public PathEndingLocation(double x, double y) {
        super(x, y, Color.RED);
    }
}
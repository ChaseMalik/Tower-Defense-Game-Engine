package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;

/**
 * An interface for building the path. Provides a means of providing
 * the Authoring Controller with the path after it has been drawn.
 * @author Austin Kyker
 *
 */
public interface IPathConfiguring {
    void configurePath (Path path);

    void setBackground (String imageFileName);

    void setTowerRegions (boolean[][] backendTowerRegions);
}

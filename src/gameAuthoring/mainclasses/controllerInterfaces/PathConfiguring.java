package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.pathBuilding.pathComponents.Path;


public interface PathConfiguring {
    void configurePath (Path path);

    void setBackground (String imageFileName);

    void setTowerRegions (boolean[][] backendTowerRegions);
}

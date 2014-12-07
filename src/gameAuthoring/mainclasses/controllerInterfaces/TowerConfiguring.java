package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.BaseEnemy;
import java.util.List;


public interface TowerConfiguring {
    void configureTowers (List<TowerUpgradeGroup> towers);

    List<BaseEnemy> fetchEnemies ();
}

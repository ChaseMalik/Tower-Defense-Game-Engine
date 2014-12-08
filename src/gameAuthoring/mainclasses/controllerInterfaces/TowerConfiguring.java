package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameEngine.actors.BaseEnemy;
import java.util.List;

/**
 * An interface for building towers. The Tower Building Scene requires
 * the collection of enemies in order to select which enemies each tower
 * can shoot.
 * @author Austin Kyker
 *
 */
public interface TowerConfiguring {
    void configureTowers (List<TowerUpgradeGroup> towers);

    List<BaseEnemy> fetchEnemies ();
}

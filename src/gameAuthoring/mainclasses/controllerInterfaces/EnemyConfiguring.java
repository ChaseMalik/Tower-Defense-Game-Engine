package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseEnemy;
import java.util.List;

/**
 * An interface for the enemy building scene.
 * @author Austin Kyker
 *
 */
public interface EnemyConfiguring {
    void configureEnemies (List<BaseEnemy> enemies);

    List<BackendRoute> fetchEnemyRoutes ();
}

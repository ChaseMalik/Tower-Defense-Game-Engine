package gameAuthoring.mainclasses.controllerInterfaces;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseEnemy;
import java.util.List;

public interface EnemyConfiguring {
    void configureEnemies(List<BaseEnemy> enemies);
    List<BackendRoute> fetchEnemyRoutes();
}

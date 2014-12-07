package gameAuthoring.mainclasses.controllerInterfaces;

import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.List;


public interface LevelConfiguring {
    void configureLevels (List<BaseLevel> levels);

    List<BaseEnemy> fetchEnemies ();
}

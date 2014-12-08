package gameAuthoring.mainclasses.controllerInterfaces;

import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.List;

/**
 * An interface for configuring levels. The level building scene
 * needs access to the enemies, but not the towers.
 * @author Austin Kyker
 *
 */
public interface ILevelConfiguring {
    void configureLevels (List<BaseLevel> levels);

    List<BaseEnemy> fetchEnemies ();
}

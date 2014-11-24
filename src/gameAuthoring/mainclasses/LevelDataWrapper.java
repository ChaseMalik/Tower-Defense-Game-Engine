package gameAuthoring.mainclasses;

import gameEngine.levels.BaseLevel;
import java.util.List;

public class LevelDataWrapper {
    public List<BaseLevel> levels;
    public LevelDataWrapper(List<BaseLevel> levels) {
        this.levels = levels;
    }
}

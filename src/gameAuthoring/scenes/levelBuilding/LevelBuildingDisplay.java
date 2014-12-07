package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.VBox;


public class LevelBuildingDisplay extends VBox {

    private List<BaseEnemy> myEnemies;
    private List<LevelDisplayCell> myLevelCells;

    public LevelBuildingDisplay (List<BaseEnemy> enemies) {
        myEnemies = enemies;
        myLevelCells = new ArrayList<LevelDisplayCell>();
        addLevel();
    }

    public void addLevel () {
        int numLevels = myLevelCells.size();
        LevelDisplayCell cell = new LevelDisplayCell(myEnemies, numLevels);
        myLevelCells.add(cell);
        this.getChildren().add(cell);
    }

    public boolean isAllUserInputIsValid () {
        return myLevelCells.stream().filter(cell -> cell.isUserInputValid()).count() > 0;
    }

    public List<BaseLevel> transformToLevels () {
        List<BaseLevel> levels = new ArrayList<BaseLevel>();
        levels = myLevelCells.stream()
                .map(cell -> cell.generateLevel())
                .collect(Collectors.toList());
        return levels;
    }
}

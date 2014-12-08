package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * A ScrollPane that allows the user to build and visualize all of the levels.
 * @author Austin Kyker
 *
 */
public class LevelBuildingDisplay extends ScrollPane {

    private static final double HEIGHT = 600;

    private List<BaseEnemy> myEnemies;
    private List<LevelDisplayCell> myLevelCells;
    private VBox myLevelsContainer;
    private Pane myPane;
    
    public LevelBuildingDisplay (List<BaseEnemy> enemies, Pane pane) {
        myEnemies = enemies;
        myLevelCells = new ArrayList<LevelDisplayCell>();
        myLevelsContainer = new VBox();
        this.setContent(myLevelsContainer);
        this.setPrefHeight(HEIGHT);
        myPane = pane;
        addLevel();
    }

    public void addLevel () {
        int numLevels = myLevelCells.size();
        LevelDisplayCell cell = new LevelDisplayCell(myEnemies, numLevels, myPane);
        myLevelCells.add(cell);
        myLevelsContainer.getChildren().add(cell);
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

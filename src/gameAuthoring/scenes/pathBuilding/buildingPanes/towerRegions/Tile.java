package gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A class to represents the tiles in the grid used by the user to select
 * the regions where towers can and cannot be placed.
 * @author Austin Kyker
 *
 */
public class Tile extends Rectangle {

    protected static final double WIDTH =
            BuildingPane.DRAW_SCREEN_WIDTH / TowerRegionsPane.NUM_COLS;
    protected static final double HEIGHT =
            AuthorController.SCREEN_HEIGHT / TowerRegionsPane.NUM_ROWS;

    private static final double TRANSLUCENT = 0.2;
    private static final double LESS_TRANSLUCENT = 0.5;

    private boolean isSelected;

    private int myRow;
    private int myCol;
    
    public Tile (int row, int col) {
        this.setX(col * Tile.WIDTH);
        this.setY(row * Tile.HEIGHT);
        myRow = row;
        myCol = col;
        this.isSelected = false;
        this.setFill(new Color(1, 0, 0, TRANSLUCENT));
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setOnMouseClicked(event -> toggleSelection());
        this.setOnDragDetected(event -> {
            this.startFullDrag();
            toggleSelection();
        });
        this.setOnMouseDragEntered(mouseEvent -> toggleSelection());
    }

    public int getRow() {
    	return myRow;
    }
    
    public int getColumn() {
    	return myCol;
    }
    
    public void toggleSelection () {
        this.setFill(this.isSelected ? new Color(1, 0, 0, TRANSLUCENT)
                                    : new Color(1, 0, 0, LESS_TRANSLUCENT));
        this.isSelected = !isSelected;
    }

    public boolean isSelected () {
        return this.isSelected;
    }
}

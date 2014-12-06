package gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TowerRegionsPane extends BuildingPane {

    protected static final int NUM_COLS = 20;
    protected static final int NUM_ROWS = 20;
    
    private GridPane myPane;
    private Path myPath;
    private boolean[][] myBackendGrid;

    public TowerRegionsPane (Group group, Path path) {
        super(group);  
        myPath = path;
        myPane = new GridPane();
    }
    
    @Override
    public void executeEnterFunction() {
        myPane.getChildren().clear();
        myPane.setPrefSize(BuildingPane.DRAW_SCREEN_WIDTH, 
                           AuthorController.SCREEN_HEIGHT);
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                Tile tile = new Tile(row, col);
                myPane.add(tile, col, row);
                if(myPath.intersects(tile)) {
                    tile.toggleSelection();                  
                }
            }
        }
        myGroup.getChildren().add(myPane);
    }
    
    @Override
    public void executeExitFunction () {  
        myGroup.getChildren().remove(myPane);
    }

    @SuppressWarnings("static-access")
    private void generateBackendGrid () {
        myBackendGrid = new boolean[NUM_ROWS][NUM_COLS];
        for(Node n:myPane.getChildren()) {
            myBackendGrid[myPane.getRowIndex(n)][myPane.getColumnIndex(n)] = 
                    !((Tile) n).isSelected();
        }
    }
    
    public boolean[][] getBackendTowerRegions() {
        generateBackendGrid();
        return myBackendGrid;
    }
}
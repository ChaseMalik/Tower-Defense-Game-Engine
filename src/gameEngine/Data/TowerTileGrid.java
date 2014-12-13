package gameEngine.Data;

public class TowerTileGrid {

	private boolean myChangedFlag;
	
	private boolean[][] myTowerTileGrid;
	private int myRowSize;
	private int myColSize;

	public TowerTileGrid(int rowSize, int colSize) {
		myTowerTileGrid = new boolean[rowSize][colSize];
		myChangedFlag = false;
		myRowSize = rowSize;
		myColSize = colSize;
	}
	
	public boolean isInRange(int row, int col) {
		return row >= 0 && row < myRowSize && col >= 0 && col < myColSize;
	}
	
	public boolean checkTowerTile(int row, int col) {
		return myTowerTileGrid[col][row];
	}
	
	public void setTowerTile(int row, int col, boolean towerOn) {
		myTowerTileGrid[col][row] = towerOn;
		setChangedStatus(true);
	}

	private void setChangedStatus(boolean status) {
		myChangedFlag = status;
	}
	
	public void setAsNotChanged() {
		setChangedStatus(false);
	}
	
	public boolean hasBeenChanged() {
		return myChangedFlag;
	}
}

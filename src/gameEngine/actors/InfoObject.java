package gameEngine.actors;

import gameEngine.TowerTileGrid;

import java.util.List;

import javafx.scene.layout.GridPane;

public class InfoObject {
	//Timmy likes traps
	private List<BaseActor> myEnemiesInRange;
	private List<BaseActor> myTowersInRange;
	private List<BaseActor> myProjectilesInRange;
	private TowerTileGrid myTowerTileGrid;
	private GridPane myTowerTilePane;
	
	public InfoObject(List<BaseActor> enemies, List<BaseActor> towers,
			List<BaseActor> projectiles, TowerTileGrid towerTileGrid,
			GridPane towerTilePane) {
		myEnemiesInRange = enemies;
		myTowersInRange = towers;
		myProjectilesInRange = projectiles;
		myTowerTileGrid = towerTileGrid;
		myTowerTilePane = towerTilePane;
	}

	public TowerTileGrid getTowerTileGrid() {
		return myTowerTileGrid;
	}
	
	public GridPane getmTowerTilePane() {
		return myTowerTilePane;
	}
	
	public List<BaseActor> getEnemiesInRange() {
		return myEnemiesInRange;
	}

	public List<BaseActor> getTowersInRange() {
		return myTowersInRange;
	}

	public List<BaseActor> getProjectilesInRange() {
		return myProjectilesInRange;
	}

}

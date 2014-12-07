package gameEngine;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameEngine.actors.BaseEnemy;

import com.sun.javafx.geom.Point2D;

import utilities.pathfinding.AStarPathFinder;

public class GridPathFinder extends AStarPathFinder<Point2D> {

	private static int[][] myDefault2DDirections = { { -1, -1 }, { -1, 0 },
			{ -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
			{ 1, 1 } };

	private boolean[][] myTowerLocations;

	@Override
	public Number getCost(Point2D beginningNode, Point2D endingNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTowerLocation(boolean[][] towerLocations) {
		myTowerLocations = towerLocations;
	}

	public void getPath(BaseEnemy enemy, GridPane tilePane,
			boolean[][] tileLocations) {
		Node enemyNode = enemy.getNode();
		List<Node> enemyTiles = tilePane.getChildren().stream()
				.filter(node -> node.intersects(enemyNode.getBoundsInLocal()))
				.collect(Collectors.toList());
		List<Node> enemyCenterTile = enemyTiles.stream()
				.filter(node -> node.contains(enemy.getX(), enemy.getY()))
				.collect(Collectors.toList());
		
		//findPath(new Point2D(enemyCenterTile.), null);
		// enemyCenterTile.getCol()), destination);
	}

	@Override
	public Iterable<Point2D> getNeighbors(Point2D node) {
		// for (int[] direction : myDefault2DDirections) {
		// int neighborX = x + direction[0];
		// int neighborY = y + direction[1];
		// if (neighborX >= 0 && neighborX < myMap.length && neighborY >= 0
		// && neighborY < myMap[0].length) {
		// nodeList.add(myMap[neighborX][neighborY]);
		// }
		// }
		return null;
	}

	@Override
	public int breakTie(Point2D node, Point2D other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getHeuristicValue(Point2D node, Point2D destination) {
		// TODO Auto-generated method stub
		return null;
	}

}

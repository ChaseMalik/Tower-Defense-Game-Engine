package gameEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseEnemy;
import utilities.pathfinding.AStarPathFinder;

public class GridPathFinder extends AStarPathFinder<Point2D> {

	private static final int[][] SQUARE_2D_DIRECTION = { { -1, -1 }, { -1, 0 },
			{ -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
			{ 1, 1 } };

	private boolean[][] myTowerLocations;

	@Override
	public Number getCost(Point2D beginningNode, Point2D endingNode) {
		return 1; 
	}

	public void setTowerLocation(boolean[][] towerLocations) {
		myTowerLocations = towerLocations;
	}

	public BackendRoute getPath(BaseEnemy enemy, GridPane tilePane,
			boolean[][] tileLocations) {
		myTowerLocations = tileLocations;
		Node enemyNode = enemy.getNode();
		List<Node> enemyTiles = tilePane.getChildren().stream()
				.filter(node -> node.intersects(enemyNode.getBoundsInLocal()))
				.collect(Collectors.toList());
		List<Node> enemyCenterTileList = enemyTiles.stream()
				.filter(node -> node.contains(enemy.getX(), enemy.getY()))
				.collect(Collectors.toList());
		Node enemyCenterTile = enemyCenterTileList.get(0);
		
		VisibilityPoint goal = enemy.getGoal();
		
		findPath(new Point2D(enemyCenterTile.getLayoutX(), enemyCenterTile.getLayoutY()), null);
		
		return null;
	}

	@Override
	public Iterable<Point2D> getNeighbors(Point2D node) {
		List<Point2D> nodeList = new ArrayList<>();
		for (int[] direction : SQUARE_2D_DIRECTION) {
			int neighborRow = (int)node.getX() + direction[0];
			int neighborCol = (int)node.getY() + direction[1];
			if(!myTowerLocations[neighborRow][neighborCol]) {
				nodeList.add(new Point2D(neighborRow, neighborCol));
			}
		}
		return nodeList;
	}

	@Override
	public int breakTie(Point2D node, Point2D other) {
		Integer x = (int) node.getX();
        Integer otherX = (int) other.getX();
        int xCompareValue = x.compareTo(otherX);
        return xCompareValue == 0 ? ((Integer)(int)node.getY()).compareTo((Integer)(int)other.getY())
                                 : xCompareValue;
	}

	@Override
	public Number getHeuristicValue(Point2D node, Point2D destination) {
		return 0;
	}

}

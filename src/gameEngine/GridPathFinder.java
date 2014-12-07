package gameEngine;

import com.sun.javafx.geom.Point2D;

import utilities.pathfinding.AStarPathFinder;

public class GridPathFinder extends AStarPathFinder<Point2D> {

	private static int[][] myDefault2DDirections = { { -1, -1 }, { -1, 0 },
			{ -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
			{ 1, 1 } };
	
	private boolean[][] towerRegions;

	@Override
	public Number getCost(Point2D beginningNode, Point2D endingNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Point2D> getNeighbors(Point2D node) {
		
		for (int[] direction : myDefault2DDirections) {
			int neighborX = x + direction[0];
			int neighborY = y + direction[1];
			if (neighborX >= 0 && neighborX < myMap.length && neighborY >= 0
					&& neighborY < myMap[0].length) {
				nodeList.add(myMap[neighborX][neighborY]);
			}
		}
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

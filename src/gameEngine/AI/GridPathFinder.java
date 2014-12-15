// This entire file is part of my masterpiece.
// YOUR NAME

package gameEngine.AI;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.TowerRegionsPane;
import gameEngine.Data.TowerTileGrid;
import gameEngine.actors.BaseEnemy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import utilities.pathfinding.AStarPathFinder;


public class GridPathFinder extends AStarPathFinder<Point2D> {

    private static final int DIRECTION_SIZE = 2;
    private static final int[][] SQUARE_2D_DIRECTION = { { -1, -1 }, { -1, 0 },
                                                        { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 },
                                                        { 1, 0 },
                                                        { 1, 1 } };

    private Collection<int[]> myEnemyTileDirections;
    private TowerTileGrid myTowerLocations;

    @Override
    public Number getCost (Point2D beginningNode, Point2D endingNode) {
        return beginningNode.distance(endingNode);
    }

    public List<Point2D> findAvoidingPath (BaseEnemy enemy, GridPane tilePane,
                                  TowerTileGrid tileLocations) {
        myTowerLocations = tileLocations;
        Node enemyNode = enemy.getNode();
        List<Node> enemyTiles = tilePane.getChildren().stream()
                .filter(node -> node.intersects(enemyNode.getBoundsInLocal()))
                .collect(Collectors.toList());
        List<Node> enemyCenterTileList = enemyTiles.stream()
                .filter(node -> node.contains(enemy.getX(), enemy.getY()))
                .collect(Collectors.toList());
        Tile enemyCenterTile = (Tile) enemyCenterTileList.get(0);
        mapEnemyArea(enemyTiles, enemyCenterTile);
        Point2D goal = enemy.getGoal().getPoint();
        Tile goalTile = getGoalTile(tilePane, goal);
        List<Point2D> tilePath =
                findPath(convertTileToGridPoint2D(enemyCenterTile), convertTileToGridPoint2D(goalTile));
        List<Point2D> convertedPath = null;
        if (tilePath != null) {
            convertedPath = convertTilePath(tilePath);
            convertedPath.set(convertedPath.size() - 1, goal);
        }
        return convertedPath;
    }

    private List<Point2D> convertTilePath (List<Point2D> tilePath) {
        List<Point2D> convertedPath;
        convertedPath = new ArrayList<>();
        for (Point2D pathTile : tilePath) {
            double row = pathTile.getX();
            double column = pathTile.getY();
            double convertedX =
                    column * BuildingPane.DRAW_SCREEN_WIDTH / TowerRegionsPane.NUM_COLS;
            double convertedY =
                    row * AuthorController.SCREEN_HEIGHT / TowerRegionsPane.NUM_ROWS;
            convertedPath.add(new Point2D(convertedX, convertedY));
        }
        return convertedPath;
    }

    private void mapEnemyArea (List<Node> enemyTiles, Tile enemyCenterTile) {
        myEnemyTileDirections = new ArrayList<>();
        for (Node enemyTileNode : enemyTiles) {
            Tile enemyTile = (Tile) enemyTileNode;
            int rowDifference = enemyTile.getRow() - enemyCenterTile.getRow();
            int colDifference = enemyTile.getColumn()
                                - enemyCenterTile.getColumn();
            if (rowDifference == 0 && colDifference == 0) {
                continue;
            }
            int[] direction = new int[DIRECTION_SIZE];
            direction[0] = rowDifference;
            direction[1] = colDifference;
            myEnemyTileDirections.add(direction);
        }
    }

    private Point2D convertTileToGridPoint2D (Tile tile) {
        return new Point2D(tile.getRow(), tile.getColumn());
    }

    private Tile getGoalTile (GridPane tilePane, Point2D goal) {
        List<Node> goalNodeList = tilePane.getChildren().stream()
                .filter(node -> node.contains(goal.getX(), goal.getY()))
                .collect(Collectors.toList());
        Tile goalTile = (Tile) goalNodeList.get(0);
        return goalTile;
    }

    @Override
    public Iterable<Point2D> getNeighbors (Point2D node) {

        List<Point2D> nodeList = new ArrayList<>();
        for (int[] direction : SQUARE_2D_DIRECTION) {
            int neighborRow = (int) node.getX() + direction[0];
            int neighborCol = (int) node.getY() + direction[1];
            if (myTowerLocations.isInRange(neighborRow, neighborCol)) {
                boolean movementAvailable = true;
                for (int[] enemyTileDirection : myEnemyTileDirections) {
                    int enemyRowAfterMovement = neighborRow + enemyTileDirection[0];
                    int enemyColAfterMovement = neighborCol + enemyTileDirection[1];
                    if (!myTowerLocations.isInRange(enemyRowAfterMovement,
                                                    enemyColAfterMovement) ||
                        myTowerLocations.checkTowerTile(
                                                        enemyRowAfterMovement,
                                                        enemyColAfterMovement)) {
                        movementAvailable = false;
                        break;
                    }
                }
                if (movementAvailable) {
                    nodeList.add(new Point2D(neighborRow, neighborCol));
                }
            }
        }
        return nodeList;
    }

    @Override
    public int breakTie (Point2D node, Point2D other) {
        Integer col = (int) node.getY();
        Integer otherCol = (int) other.getY();
        int colCompareValue = col.compareTo(otherCol);
        return colCompareValue == 0 ? ((Integer) (int) node.getX())
                .compareTo((Integer) (int) other.getX()) : colCompareValue;
    }

    @Override
    public Number getHeuristicValue (Point2D node, Point2D destination) {
        return node.distance(destination);
    }

}

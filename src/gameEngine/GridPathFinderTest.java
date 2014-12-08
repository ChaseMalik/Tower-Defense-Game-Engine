package gameEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.behaviors.AIMovement;
import gameEngine.actors.behaviors.IBehavior;

import org.junit.Test;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;

public class GridPathFinderTest {

	@Test
	public void testPathFinderLogic() {
		BaseEnemy enemy = createEnemy();
		enemy.getNode().setX(40);
		enemy.getNode().setY(40);
		GridPane referencePane = createReferencePane();
		TowerTileGrid tileGrid = new TowerTileGrid(20, 20);
		List<Node> enemyTiles = referencePane
				.getChildren()
				.stream()
				.filter(node -> node.intersects(enemy.getNode()
						.getBoundsInLocal())).collect(Collectors.toList());
		System.out.println("Enemy Tiles");
		printNodes(enemyTiles);
		ImageView towerImage = createTowerNode();
		List<Node> towerTiles = referencePane
				.getChildren()
				.stream()
				.filter(node -> node.intersects(towerImage
						.getBoundsInLocal())).collect(Collectors.toList());
		for(Node node : towerTiles) {
			Tile tile = (Tile) node;
			tileGrid.setTowerTile(tile.getRow(), tile.getColumn(), true);
		}
		System.out.println("Tower tiles");
		printNodes(towerTiles);
//		for(int i = 3; i <= 5; i ++) {
//			for(int j = 2; j <= 3; j++) {
//				tileGrid.setTowerTile(i, j, true);
//			}
//		}
		GridPathFinder finder = new GridPathFinder();
		List<Point2D> path = finder.getPath(enemy, referencePane, tileGrid);
		//System.out.println(path);
		
	}
	
	private void printNodes(Collection<Node> enemyTiles) {
		for (Node enemyNode : enemyTiles) {
			Tile tile = (Tile) enemyNode;
			System.out.println(tile.getRow() + " , " + tile.getColumn());
		}
	}
	public ImageView createTowerNode() {
		ImageView towerImage = StringToImageViewConverter
				.getImageView(
						50,
						50,
						"/Users/Duke/Desktop/Compsci308/voogasalad_LosTorres/Games/AIBelieve/enemyImages/zombie2.jpg");
		towerImage.setX(120);
		towerImage.setY(120);
		return towerImage;
	}
	private BaseEnemy createEnemy() {
		AIMovement movement = new AIMovement(2);
		Map<String, IBehavior> behaviorMap = new HashMap<>();
		behaviorMap.put("movement", movement);

		BackendRoute route = new BackendRoute(new Point2D(20, 20), new Point2D(
				300, 300));
		List<BackendRoute> routelist = new ArrayList<BackendRoute>();
		routelist.add(route);
		BaseEnemy enemy = new BaseEnemy(
				behaviorMap,
				"/Users/Duke/Desktop/Compsci308/voogasalad_LosTorres/Games/AIBelieve/enemyImages/zombie2.jpg",
				"zom", 2, 0, routelist);
		return enemy;
	}

	private GridPane createReferencePane() {
		int rows = 20;
		int cols = 20;
		GridPane pane = new GridPane();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Tile towerTile = new Tile(row, col);
				towerTile.setVisible(false);
				pane.add(towerTile, col, row);
			}
		}
		return pane;
	}
}

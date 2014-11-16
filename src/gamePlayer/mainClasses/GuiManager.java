package gamePlayer.mainClasses;

import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiFeatures.TowerPlacer;
import gamePlayer.guiItems.statsBoard.GameStats;
import gamePlayer.guiItems.statsBoard.StatsBoard;
import gamePlayer.guiItemsListeners.StatsBoardListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Class controls all GUI items and MUST implement ALL of the interfaces in the
 * guiItemsListeners package The game engine accesses GUI resources through this
 * class
 * 
 * @author allankiplagat
 *
 */
public class GuiManager implements VoogaMenuBarListener, StatsBoardListener,
		StoreListener
{

	private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
	private DummyGameManager myGameManager;
	private Stage myStage;
	private StatsBoard myStatsBoard;
	private Group myRoot;

	public GuiManager(Stage stage, DummyGameManager manager) {
		myGameManager = manager;
		myStage = stage;
		GuiConstants.GUI_MANAGER = this;
		myRoot = GuiBuilder.getInstance(guiBuilderPropertiesPath).build(stage);
	}

	@Override
	public void loadGame() {
		File file = FileLoader.getInstance().load(myStage);
		if (file != null) {
			System.out.println(file.getAbsolutePath() + "\n");
		}
	}

	@Override
	public void saveGame() {
		System.out.println("Save game");
	}

	@Override
	public void registerStatsBoard(StatsBoard statsBoard) {
		myStatsBoard = statsBoard;
	}

	public void setGameStats(List<GameStats> stats) {
		myStatsBoard.setGameStats(stats);
	}

	@Override
	public void buyItem(String itemID) {
		
		// TODO : Check to make sure I have enough money
		System.out.println("Buy Item: " + itemID);
		
		TowerPlacer.getInstance().placeItem(itemID, myRoot);
	}
	
	public void addItem(String itemID, Dimension2D location) {
		System.out.println("Add Item: " + itemID);
	}
}

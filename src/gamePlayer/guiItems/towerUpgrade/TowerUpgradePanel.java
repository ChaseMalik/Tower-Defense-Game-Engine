package gamePlayer.guiItems.towerUpgrade;


import java.io.File;

import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItems.towerUpgrade.panelItems.BackgroundLabel;
import gamePlayer.guiItems.towerUpgrade.panelItems.NameLabel;
import gamePlayer.guiItems.towerUpgrade.panelItems.SellButton;
import gamePlayer.guiItems.towerUpgrade.panelItems.TowerIcon;
import gamePlayer.guiItems.towerUpgrade.panelItems.UpgradeButton;
import gamePlayer.guiItems.towerUpgrade.panelItems.UpgradePanelItem;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * 
 * @author Greg Lyons
 *
 */

public class TowerUpgradePanel extends Pane implements GuiItem {

	private HBox myUpgradeBox;
	private Label unselectedLabel;
	private UpgradeItemsCollection<UpgradePanelItem> upgradePanelItems;
	private ImageView myTowerImageView;
	private XMLParser myParser;
	private TowerIndicator activeIndicator;
	private UpgradeListener myListener;

	@Override
	public void initialize(Dimension2D containerSize) {

		myListener = GuiConstants.GUI_MANAGER;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
		myParser = new XMLParser(new File(propertiesPath)); 

		Dimension2D mySize = makeDim(containerSize, "SizeRatio");
		this.setPrefSize(mySize.getWidth(), mySize.getHeight());

		makePanelItems(mySize);	
		myUpgradeBox = new HBox();
		myUpgradeBox.getChildren().addAll(upgradePanelItems.getItemNodes());

		myListener.registerUpgradePanel(this);
		unselectedLabel = new BackgroundLabel(UpgradeConstants.NO_SELECTED_TOWER, mySize, UpgradeConstants.STONE_PATH);
		this.getChildren().add(unselectedLabel);
	}

	private void makePanelItems(Dimension2D mySize) {
		Dimension2D myIconSize = makeDim(mySize, "IconSizeRatio");
		Dimension2D myButtonSize = makeDim(mySize, "ButtonSizeRatio");
		Dimension2D myLabelSize = makeDim(mySize, "LabelRatio");

		UpgradePanelItem myIcon = new TowerIcon(myIconSize, null);
		UpgradePanelItem myName = new NameLabel(myLabelSize, UpgradeConstants.STONE_PATH, null);
		UpgradePanelItem upgradeButton = new UpgradeButton(Color.ORANGERED, myButtonSize, UpgradeConstants.UP_PATH, event -> doUpgrade());	
		UpgradePanelItem sellButton = new SellButton(Color.GREEN, myButtonSize, UpgradeConstants.SELL_PATH, event -> sell());

		upgradePanelItems = new UpgradeItemsCollection<UpgradePanelItem>();
		upgradePanelItems.addItems(myIcon, myName, upgradeButton, sellButton);
	}

	private Dimension2D makeDim(Dimension2D containerSize, String tag) {
		Dimension2D ratio = myParser.getDimension(tag);
		return RatiosToDim.convert(containerSize, ratio);
	}

	public void deselectTower(){
		this.getChildren().clear();
		this.getChildren().add(unselectedLabel);
	}

	public void setCurrentTower(TowerInfoObject current, ImageView towerImageView, TowerIndicator indicator){
		this.getChildren().clear();
		for (UpgradePanelItem item: upgradePanelItems) {
			((UpgradePanelItem)item).setCurrentTower(current);
		}
		myTowerImageView = towerImageView;
		activeIndicator = indicator;
		this.getChildren().add(myUpgradeBox);
	}

	private void sell(){
		myListener.sellTower(myTowerImageView, activeIndicator);
		deselectTower();
	}

	private void doUpgrade(){
		if (myListener.upgradeTower(myTowerImageView))
			deselectTower();
	}

	@Override
	public Node getNode() {
		return this;
	}

}

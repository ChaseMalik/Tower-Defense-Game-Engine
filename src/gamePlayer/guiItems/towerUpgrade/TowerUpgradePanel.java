// This entire file is part of my masterpiece.
// GREG LYONS

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

/**
 * 
 * @author Greg Lyons
 * 
 * The TowerUpgradePanel is a HUD-type panel on-screen during gameplay that allows for selected towers
 * to be upgraded or sold. When a tower is clicked, the TowerUpgradePanel modifies its text
 * and images to reflect the tower currently selected. It does so by iterating over its 
 * UpgradeItemCollection of UpgradePanelItems, calling setCurrentTower on each one.
 * 
 * myUpgradeBox - holds the panel items
 * unselectedLabel - placeholder for when no tower is selected
 * upgradePanelItems - collection of UpgradePanelItems
 * myTowerImageView - the ImageView of the currently selected tower
 * myParser - XML Parser
 * activeIndicator - an extended circle used to show which tower is selected
 * myListener - a reference to the GuiManager, but can only call the methods relevant to upgrading/selling
 *
 */

public class TowerUpgradePanel extends Pane implements GuiItem {

	private HBox myUpgradeBox;
	private Label unselectedLabel;
	private UpgradeItemCollection<UpgradePanelItem> upgradePanelItems;
	private ImageView myTowerImageView;
	private XMLParser myParser;
	private TowerIndicator activeIndicator;
	
	private static final UpgradeListener myListener = GuiConstants.GUI_MANAGER;

	
	/**
	 * Set up the dimensions and add items
	 */
	@Override
	public void initialize(Dimension2D containerSize) {

		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
		myParser = new XMLParser(new File(propertiesPath)); 

		Dimension2D mySize = makeDim(containerSize, UpgradeConstants.SIZE_TAG);
		this.setPrefSize(mySize.getWidth(), mySize.getHeight());

		makePanelItems(mySize);	
		myUpgradeBox = new HBox();
		myUpgradeBox.getChildren().addAll(upgradePanelItems.getItemNodes());

		myListener.registerUpgradePanel(this);
		unselectedLabel = new BackgroundLabel(UpgradeConstants.NO_SELECTED_TOWER, mySize, UpgradeConstants.STONE_PATH);
		this.getChildren().add(unselectedLabel);
	}
	
	/**
	 * 
	 * This method is where the items to be added to the panel are specified and instantiated
	 * 
	 */

	private void makePanelItems(Dimension2D mySize) {
		Dimension2D myIconSize = makeDim(mySize, UpgradeConstants.ICON_TAG);
		Dimension2D myButtonSize = makeDim(mySize, UpgradeConstants.BUTTON_TAG);
		Dimension2D myLabelSize = makeDim(mySize, UpgradeConstants.LABEL_TAG);

		UpgradePanelItem myIcon = new TowerIcon(myIconSize);
		UpgradePanelItem myName = new NameLabel(myLabelSize);
		UpgradePanelItem upgradeButton = new UpgradeButton(myButtonSize, event -> doUpgrade());	
		UpgradePanelItem sellButton = new SellButton(myButtonSize, event -> sell());

		upgradePanelItems = new UpgradeItemCollection<UpgradePanelItem>();
		upgradePanelItems.addItems(myIcon, myName, upgradeButton, sellButton);
	}

	/**
	 * Utility method for finding an XML tag and returning the appropriate size dimension
	 */
	private Dimension2D makeDim(Dimension2D containerSize, String tag) {
		Dimension2D ratio = myParser.getDimension(tag);
		return RatiosToDim.convert(containerSize, ratio);
	}
	
	/**
	 * 
	 * No tower is selected
	 */
	public void deselectTower(){
		this.getChildren().clear();
		this.getChildren().add(unselectedLabel);
	}

	/**
	 * A tower has been selected (called by GuiManager)
	 * 
	 * @param current
	 * @param towerImageView
	 * @param indicator
	 */
	public void selectTower(TowerInfoObject current, ImageView towerImageView, TowerIndicator indicator){
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

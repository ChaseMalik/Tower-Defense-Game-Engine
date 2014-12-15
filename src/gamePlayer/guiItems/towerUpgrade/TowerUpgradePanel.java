package gamePlayer.guiItems.towerUpgrade;


import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
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
	
	private UpgradePanelItem myIcon;
	private UpgradePanelItem myName;
	private UpgradePanelItem upgradeButton;
	private UpgradePanelItem sellButton;
	private UpgradeItemsCollection<UpgradePanelItem> upgradePanelItems;
	
	private ImageView myTowerImageView;
	private XMLParser myParser;
	private TowerIndicator activeIndicator;
	
	private UpgradeListener myListener;
	
	public static final String NO_SELECTED_TOWER = "Click on a tower to upgrade or sell it";
	public static final String UPGRADE_TEXT = "Upgrade to:" + "\n";
	public static final String STONE_PATH = "./src/gamePlayer/playerImages/stonewall.jpg";
	public static final String SELL_PATH = "./src/gamePlayer/playerImages/dollars.jpg";
	public static final String UP_PATH = "./src/gamePlayer/playerImages/upvote.jpg";
	
	@Override
	public void initialize(Dimension2D containerSize) {
		
		myListener = GuiConstants.GUI_MANAGER;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";

        myParser = new XMLParser(new File(propertiesPath)); 

        Dimension2D mySize = makeDim(containerSize, "SizeRatio");
        Dimension2D myIconSize = makeDim(mySize, "IconSizeRatio");
        Dimension2D myButtonSize = makeDim(mySize, "ButtonSizeRatio");
        Dimension2D myLabelSize = makeDim(mySize, "LabelRatio");
       
        this.setPrefSize(mySize.getWidth(), mySize.getHeight());
        
		unselectedLabel = new BackgroundLabel(NO_SELECTED_TOWER, mySize, STONE_PATH);
		
		myIcon = new TowerIcon(myIconSize, null);
		myName = new NameLabel(myLabelSize, STONE_PATH, null);
		upgradeButton = new UpgradeButton(Color.ORANGERED, myButtonSize, UP_PATH, event -> doUpgrade());
		sellButton = new SellButton(Color.GREEN, myButtonSize, SELL_PATH, event -> sell());
		upgradePanelItems = new UpgradeItemsCollection<UpgradePanelItem>();
		upgradePanelItems.addItems(myIcon, myName, upgradeButton, sellButton);
		
		myUpgradeBox = new HBox();
		myUpgradeBox.getChildren().addAll(upgradePanelItems.getItemNodes());


		myListener.registerUpgradePanel(this);

		this.getChildren().add(unselectedLabel);

		this.toFront();
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

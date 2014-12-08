package gamePlayer.guiItems.towerUpgrade;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * 
 * @author Greg Lyons
 *
 */

public class TowerUpgradePanel extends Pane implements GuiItem {

	
	private HBox myUpgradeBox;
	private Label unselectedLabel;
	private ImageView myIcon;
	private Label myName;
	private VBox myIconBox;
	private String myUpgradeName;
	private Button upgradeButton;
	private Button sellButton;
	private ImageView myTowerImageView;
	private XMLParser myParser;
	private TowerIndicator activeIndicator;
	
	private Dimension2D mySize;
	private Dimension2D myButtonSize;
	private Dimension2D myIconSize;
	private Dimension2D myLabelSize;
	
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
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        Dimension2D buttonRatio = myParser.getDimension("ButtonSizeRatio");
        Dimension2D labelRatio = myParser.getDimension("LabelRatio");
        Dimension2D iconRatio = myParser.getDimension("IconSizeRatio");

        mySize = RatiosToDim.convert(containerSize, sizeRatio);
        myIconSize = RatiosToDim.convert(mySize, iconRatio);
        myButtonSize = RatiosToDim.convert(mySize, buttonRatio);
        myLabelSize = RatiosToDim.convert(mySize, labelRatio);

   
        this.setPrefSize(mySize.getWidth(), mySize.getHeight());
        
		unselectedLabel = new BackgroundLabel(NO_SELECTED_TOWER, mySize, STONE_PATH);
        
		myIcon = new ImageView();
		myIcon.setFitWidth(myIconSize.getWidth());
		myIcon.setFitHeight(myIconSize.getWidth());  //same dimensions for square image
		myIcon.setStyle("-fx-stroke: green; -fx-stroke-width: 5");
		
		myName = new BackgroundLabel("", myLabelSize, STONE_PATH);
		upgradeButton = new PanelButton(Color.ORANGERED, myButtonSize, UP_PATH);
		upgradeButton.setOnAction(event -> doUpgrade());
		sellButton = new PanelButton(Color.GREEN, myButtonSize, SELL_PATH);
		sellButton.setOnAction(event -> sell());
		
		myUpgradeBox = new HBox();
		myUpgradeBox.getChildren().addAll(myIcon, myName, upgradeButton, sellButton);

		myListener.registerUpgradePanel(this);
		
		//setCurrentTower(new NullTowerInfoObject(), null, null);
		this.getChildren().add(unselectedLabel);

		this.toFront();
	}
	
	public void deselectTower(){
		this.getChildren().clear();
		this.getChildren().add(unselectedLabel);
	}
	
	public void setCurrentTower(TowerInfoObject current, ImageView towerImageView, TowerIndicator indicator){
		this.getChildren().clear();
		myIcon.setImage(StringToImageViewConverter.getImageView(myIconSize.getWidth(), myIconSize.getHeight(), current.getImageLocation()).getImage());
		myName.setText(current.getName());
		myUpgradeName = current.getMyUpgrade().getName();
		upgradeButton.setText(UPGRADE_TEXT + myUpgradeName);
		int sellPrice = current.getSellCost();
		sellButton.setText("Sell tower for:\n" + sellPrice);
		myTowerImageView = towerImageView;
		activeIndicator = indicator;
		this.getChildren().add(myUpgradeBox);
	}
	
	private void sell(){
		myListener.sellTower(myTowerImageView, activeIndicator);
		deselectTower();
		
	}
	
	private void doUpgrade(){
		myListener.upgradeTower(myTowerImageView, myUpgradeName);
		deselectTower();
	}

	

	@Override
	public Node getNode() {
		return this;
	}

}

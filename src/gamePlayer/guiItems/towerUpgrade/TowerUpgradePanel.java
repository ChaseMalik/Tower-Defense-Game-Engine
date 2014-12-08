package gamePlayer.guiItems.towerUpgrade;

import java.io.File;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;
import gameEngine.NullTowerInfoObject;
import gameEngine.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
	
	@Override
	public void initialize(Dimension2D containerSize) {
		
		myListener = GuiConstants.GUI_MANAGER;
		
		unselectedLabel = new Label(NO_SELECTED_TOWER);
		
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
        
		myIcon = new ImageView();
		myIcon.setFitWidth(myIconSize.getWidth());
		myIcon.setFitHeight(myIconSize.getWidth());  //same dimensions for square image
		
		myName = new Label();
		myName.setPrefSize(myLabelSize.getWidth(), myLabelSize.getHeight());
		myName.setAlignment(Pos.CENTER);
		myName.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
		
		upgradeButton = new Button();
		upgradeButton.setPrefSize(myButtonSize.getWidth(), myButtonSize.getHeight());
		
		sellButton = new Button();
		sellButton.setPrefSize(myButtonSize.getWidth(), myButtonSize.getHeight());
		
		myUpgradeBox = new HBox();
		myUpgradeBox.getChildren().addAll(myIcon, myName, upgradeButton, sellButton);

		myListener.registerUpgradePanel(this);
		
		//setCurrentTower(new NullTowerInfoObject(), null, null);
		this.getChildren().add(unselectedLabel);
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
		upgradeButton.setOnAction(event -> doUpgrade());
		sellButton.setText("Sell tower");
		sellButton.setOnAction(event -> sell());
		myTowerImageView = towerImageView;
		activeIndicator = indicator;
		this.getChildren().add(myUpgradeBox);
	}
	
	private void sell(){
		myListener.sellTower(myTowerImageView, activeIndicator);
	}
	
	private void doUpgrade(){
		myListener.upgradeTower(myTowerImageView, myUpgradeName);
	}

	

	@Override
	public Node getNode() {
		return this;
	}

}

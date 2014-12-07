package gamePlayer.guiItems.towerUpgrade;

import java.io.File;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.XMLParsing.XMLParser;
import gameEngine.NullTowerInfoObject;
import gameEngine.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TowerUpgradePanel extends Pane implements GuiItem {
	
	private HBox myButtonBox;
	private ImageView myIcon;
	private TextArea myName;
	private String myUpgradeName;
	private Button upgrade1Button;
	private Button sellButton;
	private ImageView myTowerImageView;
	private XMLParser myParser;
	private TowerIndicator activeIndicator;
	
	private UpgradeListener myListener;
	
	public void setCurrentTower(TowerInfoObject current, ImageView towerImageView, TowerIndicator indicator){
		myIcon.setImage(StringToImageViewConverter.getImageView(75, 75, current.getImageLocation()).getImage());
		myName.setText(current.getName());
		myUpgradeName = current.getMyUpgrade().getName();
		upgrade1Button.setText("Upgrade to:" + "\n" + myUpgradeName);
		upgrade1Button.setOnAction(event -> doUpgrade());
		sellButton.setText("Sell tower");
		sellButton.setOnAction(event -> sell());
		myTowerImageView = towerImageView;
		activeIndicator = indicator;
	}
	
	private void sell(){
		myListener.sellTower(myTowerImageView, activeIndicator);
	}
	
	private void doUpgrade(){
		myListener.upgradeTower(myTowerImageView, myUpgradeName);
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        Dimension2D mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(), containerSize.getHeight()*sizeRatio.getHeight());
        setPrefSize(mySize.getWidth(), mySize.getHeight());
		myListener = GuiConstants.GUI_MANAGER;
		myIcon = new ImageView();
		myIcon.setFitHeight(mySize.getHeight());
		myIcon.setFitWidth(mySize.getHeight());
		myName = new TextArea();
		myName.setPrefSize(mySize.getWidth()/3-mySize.getHeight(), mySize.getHeight());
		upgrade1Button = new Button();
		upgrade1Button.setPrefSize(mySize.getWidth()/3, mySize.getHeight());
		sellButton = new Button();
		sellButton.setPrefSize(mySize.getWidth()/3, mySize.getHeight());
		myButtonBox = new HBox();
		myButtonBox.getChildren().addAll(myName, myIcon, upgrade1Button, sellButton);
		this.getChildren().add(myButtonBox);
		myListener.registerUpgradePanel(this);
		setCurrentTower(new NullTowerInfoObject(), null, null);
	}

	@Override
	public Node getNode() {
		return this;
	}

}

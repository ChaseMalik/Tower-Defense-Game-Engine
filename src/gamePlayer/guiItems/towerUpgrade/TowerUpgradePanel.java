package gamePlayer.guiItems.towerUpgrade;

import gameEngine.TowerInfoObject;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TowerUpgradePanel extends Pane implements GuiItem {
	
	private HBox myButtonBox;
	private ImageView myIcon;
	private Text myName;
	private Button upgrade1Button, upgrade2Button, mySellButton;
	private Node myTowerNode;
	
	private UpgradeListener myListener;
	
	public void setCurrentTower(TowerInfoObject current, Node towerNode){
		myIcon.setImage(new Image(current.getImageLocation()));
		myName.setText(current.getName());
		upgrade1Button.setText("Upgrade to" + current.getMyUpgrade().getName());
		upgrade1Button.setOnAction(event -> doUpgrade());
		myTowerNode = towerNode;
	}
	
	private void doUpgrade(){
		myListener.upgradeTower(myTowerNode);
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		setPrefSize(containerSize.getWidth(), containerSize.getHeight());
		
		myListener = GuiConstants.GUI_MANAGER;
		myIcon = new ImageView();
		myName = new Text();
		upgrade1Button = new Button();
		upgrade2Button = new Button();
		mySellButton = new Button();
		myButtonBox = new HBox();
		myButtonBox.getChildren().addAll(myName, myIcon, upgrade1Button, upgrade2Button, mySellButton);
		getChildren().add(myButtonBox);
		upgrade1Button.setPrefSize(containerSize.getWidth()/3.0, containerSize.getHeight());
		myButtonBox = new HBox();
		myButtonBox.getChildren().addAll(myName, myIcon, upgrade1Button);
		this.getChildren().add(myButtonBox);
		myListener.registerUpgradePanel(this);
	}

	@Override
	public Node getNode() {
		return this;
	}

}

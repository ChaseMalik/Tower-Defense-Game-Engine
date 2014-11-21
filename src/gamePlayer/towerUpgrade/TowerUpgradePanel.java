package gamePlayer.towerUpgrade;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

public class TowerUpgradePanel extends Pane implements GuiItem {
	
	private HBox myButtonBox;
	private ImageView myIcon;
	private Text myName;
	private Button upgrade1Button;
	private Button upgrade2Button;
	private Button mySellButton;
	
	private UpgradeListener myListener;

	public TowerUpgradePanel() {
		super();
		myListener = GuiConstants.GUI_MANAGER;
		myIcon = new ImageView();
		myName = new Text();
		upgrade1Button = new Button();
		upgrade2Button = new Button();
		mySellButton = new Button();
		myButtonBox.getChildren().addAll(myName, myIcon, upgrade1Button, upgrade2Button, mySellButton);
		this.getChildren().add(myButtonBox);
	}
	
	public void setCurrentTower(TowerUpgradeInfo current){
		myIcon.setImage(new Image(current.getImagePath()));
		myName.setText(current.getName());
		upgrade1Button.setText(current.getLabel1());
		upgrade2Button.setText(current.getLabel2());
		mySellButton.setText("Sell for $"+current.getPrice());
		
		upgrade1Button.setOnAction(event -> doUpgrade(current.getTower1(), current.getX(), current.getY()));
		upgrade2Button.setOnAction(event -> doUpgrade(current.getTower2(), current.getX(), current.getY()));
	}
	
	private void doUpgrade(Class newTower, double x, double y){
		
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		this.setPrefSize(containerSize.getWidth(), containerSize.getHeight());
	}

	@Override
	public Node getNode() {
		return this;
	}

}

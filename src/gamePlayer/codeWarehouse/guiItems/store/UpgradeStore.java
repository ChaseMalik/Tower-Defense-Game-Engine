package gamePlayer.codeWarehouse.guiItems.store;

import gamePlayer.guiItems.GuiItem;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utilities.XMLParsing.XMLParser;

/**
 * Panel that will show up in place of the store when a tower on the map is selected. 
 * The switching behavior is handled by the Store class
 * 
 * @author brianbolze
 *
 */
public class UpgradeStore implements GuiItem {
	
	private String myTowerID;
	private XMLParser myParser;
	private ResourceBundle myTowerResources;
	
	private VBox root, myButtons;
	private Label myHeader;
	private ImageView myIcon;
	
	public UpgradeStore(String towerID) {
		myTowerID = towerID;
		myTowerResources = null;
		try {
			myTowerResources = ResourceBundle.getBundle("spriteResources."+myTowerID);
		} catch (Exception e) {
			System.out.println("Error reading properties file");
			System.exit(1); //Kill Program
		}
	}

	@Override
	public void initialize(Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
        
		root = new VBox();
		//root.setMinWidth(containerSize.getWidth());
		//root.setMinHeight(containerSize.getHeight());
		root.setPrefSize(containerSize.getWidth(), containerSize.getHeight()*0.5);
		root.alignmentProperty().setValue(Pos.TOP_CENTER);
		root.getStyleClass().add("store-pane");
		
		List<Double> heightRatios = myParser.getDoubleValuesFromTag("HeightRatios");
		buildHeader(containerSize.getWidth(), heightRatios.get(0)*containerSize.getHeight());
		buildIcon(containerSize.getWidth(), heightRatios.get(1)*containerSize.getHeight());
		buildButtons(containerSize.getWidth(), heightRatios.get(2)*containerSize.getHeight());
		
		root.getChildren().addAll(myHeader, myIcon, myButtons);
	}

	private void buildButtons(double width, double height) {
		myButtons = new VBox();
		Button sellButton = new Button("Sell");
		String upgrade1Name = (String)myTowerResources.getObject("Upgrade_1");
		String upgrade2Name = (String)myTowerResources.getObject("Upgrade_2");
		Button upgrade1Button = new Button(upgrade1Name);
		Button upgrade2Button = new Button(upgrade2Name);
		myButtons.getChildren().addAll(sellButton, upgrade1Button, upgrade2Button);
	}

	private void buildIcon(double width, double height) {
		myIcon = new ImageView();
		String imagePath = (String)myTowerResources.getObject("Tower_Image");
		myIcon.setImage(new Image("file:"+imagePath,
				0, height, true, false));
		
	}

	private void buildHeader(double width, double height) {
		myHeader = new Label();
		myHeader.setText((String)myTowerResources.getObject("Name"));
		myHeader.setMinHeight(height);
		myHeader.setMinWidth(width);
	}

	@Override
	public Node getNode() {
		return root;
	}
}

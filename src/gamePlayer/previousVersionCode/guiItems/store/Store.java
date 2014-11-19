package gamePlayer.previousVersionCode.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.SelectTowerListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utilities.XMLParsing.XMLParser;

/**
 * 
 * Store GUI with all the possible towers you can buy. 
 * To add a new tower:
 * 		(1) Create a new Properties File named <TowerName>.properties in the spriteResources.towers package
 * 		(2) Add the <TowerName> to the Inventory List in the StoreProperties XML file in the gamePlayer.properties.guiItems package
 * 		(3) Create an Image for the Cell to be displayed in the store with a picture of the tower, its name, and its cost
 * 		(4) Put the image file in the main voogasalad directory (outside of src)
 * 
 * @author brianbolze
 */
public class Store implements GuiItem, SelectTowerListener {
	
    private XMLParser myParser;
    private Node myHeader;
    private VBox myContainer;
	private GridPane myGridPane;
	private ScrollPane myScrollPane;
	private Dimension2D myContentSize, myCellSize;
	private int myNumColumns;

	private static Store myReference = null;

    public static Store getInstance() {
        if (myReference==null) {
            myReference = new Store();
        }
        return myReference;
    }
	
	@Override
	public void initialize(Dimension2D containerSize) {
		
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
		myContainer = new VBox();
		
		double contentHeightRatio = Double.parseDouble(myParser.getValuesFromTag("HeightRatio").get(1));
		myContentSize = new Dimension2D(containerSize.getWidth(), containerSize.getHeight()*contentHeightRatio);
		
        buildHeader();
        buildInventoryContainer();
		buildInventoryItems();

		myScrollPane = new ScrollPane();
		myScrollPane.setContent(myGridPane);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myScrollPane.setMinHeight(myContentSize.getHeight());
		myScrollPane.setMaxHeight(myContentSize.getHeight());
		myScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		myContainer.getChildren().addAll(myHeader, myScrollPane);
		
		GuiConstants.GUI_MANAGER.registerTowerListener(this);
		
	}

	@Override
	public Node getNode() {
		return myContainer;
	}
	
	@Override
	public void selectTower(String towerID) {
		myContainer.getChildren().remove(1);
		UpgradeStore store = new UpgradeStore(towerID);
		store.initialize(myContentSize);
		myContainer.getChildren().add(1, store.getNode());
	}
	
	@Override
	public void deselectTower() {
		myContainer.getChildren().remove(1);
		myContainer.getChildren().add(1, myScrollPane);
	}
	
	private void buildHeader() {
		Label storeLabel = new Label();
		storeLabel.setText(myParser.getValuesFromTag("HeaderTitle").get(0));
		storeLabel.alignmentProperty().set(Pos.CENTER);
		double headerHeightRatio = Double.parseDouble(myParser.getValuesFromTag("HeightRatio").get(0));
		storeLabel.setMinWidth(myContentSize.getWidth());
		storeLabel.setMinHeight(myContentSize.getHeight()*headerHeightRatio);
		myHeader = storeLabel;
		myHeader.getStyleClass().add("header-2");
	}
	
	private void buildInventoryContainer() {
		myNumColumns = myParser.getIntegerValuesFromTag("Columns").get(0);

		myGridPane = new GridPane();
		myGridPane.setVgap(myParser.getDoubleValuesFromTag("VPadding").get(0)/myNumColumns);
		myGridPane.setHgap(myParser.getDoubleValuesFromTag("HPadding").get(0)/myNumColumns);
		myGridPane.setMaxWidth(myContentSize.getWidth());
		myGridPane.getStyleClass().add("store-pane");
		
		double iconSize = myContentSize.getWidth()/myNumColumns - myGridPane.getHgap();
		myCellSize = new Dimension2D(iconSize, iconSize); //Square Icons
		
	}
	
	private void buildInventoryItems() {
		List<String> inventoryList = myParser.getValuesFromTag("InventoryList");
		int col = 0;
		int row = 0;
		for (String itemName:inventoryList) {
			GuiItem cell = new StoreItemCell(itemName);
			cell.initialize(myCellSize);
			myGridPane.add(cell.getNode(), col, row);
			col++;
			if (col == myNumColumns) {
				col = 0;
				row++;
			}
		}
	}
	
}

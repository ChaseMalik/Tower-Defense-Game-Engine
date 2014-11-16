package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import utilities.XMLParsing.XMLParser;

/**
 * 
 * Store GUI with all the possible towers you can buy. 
 * To add a new tower:
 * 		(1) Create a new Properties File named <TowerName>.properties in the spriteResources.towers package
 * 		(2) Add the <TowerName> to the Inventory List in the StoreProperties XML file in the gamePlayer.properties.guiItems package
 * 		(3) Create an Image for the Cell to be displayed in the store with a picture of the tower, its name, and its cost
 * 		(4) Put the image file in the main voogasalad directory (outside of src)
 * @author brianbolze
 */
public class Store implements GuiItem {
	
    private XMLParser myParser;
	private GridPane myGridPane;
	private ScrollPane myScrollPane;
	private Dimension2D myCellSize;
	private int myNumColumns;

	@Override
	public void initialize(Dimension2D containerSize) {
		
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
		
		myNumColumns = myParser.getIntegerValuesFromTag("Columns").get(0);

		myGridPane = new GridPane();
		myGridPane.setVgap(myParser.getDoubleValuesFromTag("VPadding").get(0)/myNumColumns);
		myGridPane.setHgap(myParser.getDoubleValuesFromTag("HPadding").get(0)/myNumColumns);
		myGridPane.setMaxWidth(containerSize.getWidth());
		
		double iconSize = (containerSize.getWidth() - myGridPane.getHgap())/myNumColumns;
		myCellSize = new Dimension2D(iconSize, iconSize); //Square Icons
		
		myScrollPane = new ScrollPane();
		myScrollPane.setContent(myGridPane);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myScrollPane.setMinHeight(containerSize.getHeight());
		myScrollPane.setMaxHeight(containerSize.getHeight());
		myScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		buildInventoryItems(containerSize);
	}

	@Override
	public Node getNode() {
		return myScrollPane;
	}
	
	private void buildInventoryItems(Dimension2D size) {
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

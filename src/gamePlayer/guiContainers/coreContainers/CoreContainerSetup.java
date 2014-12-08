package gamePlayer.guiContainers.coreContainers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiElement;

public class CoreContainerSetup implements GuiContainer{

	private XMLParser myParser;
	private Dimension2D mySize;
	private Region myRegion;
	private List<Node> myChildren;
	
	public void setRegion(Region r){
		myRegion = r;
		myChildren = new ArrayList<Node>();
	}
	
	public void setDimensions(double width, double height, Dimension2D containerSize) {
		Dimension2D mySize = null;
		if (GuiConstants.DYNAMIC_SIZING) {
			mySize = new Dimension2D(width, height);
		} else {
			mySize = containerSize;
		}
		myRegion.setMinSize(mySize.getWidth(), mySize.getHeight());
		myRegion.setPrefSize(mySize.getWidth(), mySize.getHeight());
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH
				+ myPropertiesPath + this.getClass().getSimpleName() + ".XML";
		myParser = new XMLParser(new File(propertiesPath));

		// add contained GUI elements
		List<String> myItems = myParser.getValuesFromTag("Items");
		for (String item : myItems) {
			GuiElement element = (GuiElement) Reflection.createInstance(item);
			element.initialize(mySize);
			myChildren.add(element.getNode());
		}
	}
	
	public List<Node> getChildList(){
		return myChildren;
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
	}


}

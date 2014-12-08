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

public class CoreContainerSetup {

	private XMLParser myParser;
	private Dimension2D mySize;
	private Region myRegion;
	private List<Node> myChildren;
	
	public static final String myPropertiesPath =  "guiContainers/";
	
	public void setRegion(Region r, String className){
		myRegion = r;
		myChildren = new ArrayList<Node>();
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH
				+ myPropertiesPath + className + ".XML";
		myParser = new XMLParser(new File(propertiesPath));
	}
	
	public void setDimensions(double width, double height, Dimension2D containerSize) {
		mySize = new Dimension2D(width, height);
		myRegion.setMinSize(mySize.getWidth(), mySize.getHeight());
		myRegion.setPrefSize(mySize.getWidth(), mySize.getHeight());
	}
	
	
	public void initialize(Region r, double width, double height, Dimension2D containerSize) {

		setRegion(r, r.getClass().getSimpleName());
		setDimensions(width, height, containerSize);
		
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

}

// This entire file is part of my masterpiece.
// Allan Kiplagat

package gamePlayer.guiContainers.coreContainers;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

/**
 * 
 * @author Greg Lyons, allankiplagat
 * Class initializes a container based on properties read from its properties XML file
 * It sets the container's size and initializes the container's elements
 *
 */
public class CoreContainerSetup {

    private XMLParser myParser;
    private Dimension2D mySize;
    private Region myRegion;
    private List<Node> myChildren;
    private String myPropertiesPath;

    private void setRegion(Region r, String className){
        myRegion = r;
        myChildren = new ArrayList<Node>();
        String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH
                + myPropertiesPath + className + ".XML";
        myParser = new XMLParser(new File(propertiesPath));
    }

    private void setDimensions(double width, double height, Dimension2D containerSize) {
        mySize = new Dimension2D(width, height);
        myRegion.setMinSize(mySize.getWidth(), mySize.getHeight());
        myRegion.setPrefSize(mySize.getWidth(), mySize.getHeight());
    }


    public List<Node> initialize(Region r, double width, double height, Dimension2D containerSize, String propertiesPath) {
        myPropertiesPath = propertiesPath;
        setRegion(r, r.getClass().getSimpleName());
        setDimensions(width, height, containerSize);

        // add contained GUI elements
        List<String> myItems = myParser.getValuesFromTag("Items");
        for (String item : myItems) {
            GuiElement element = (GuiElement) Reflection.createInstance(item);
            element.initialize(mySize);
            myChildren.add(element.getNode());
        }
        return myChildren;
    }
}

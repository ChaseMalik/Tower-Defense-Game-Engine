package gamePlayer.guiItems.controlDock;

import gamePlayer.guiItems.GuiItem;
import java.io.File;
import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

public class ControlDock implements GuiItem {
    private XMLParser myParser;
    private TilePane myTilePane;
    private Dimension2D mySize;
    
    @Override
    public void initialize (Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
        myTilePane = new TilePane();
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
                                             containerSize.getHeight()*sizeRatio.getHeight());
        myTilePane.setPrefSize(mySize.getWidth(),mySize.getHeight());
        myTilePane.getStyleClass().add("ControlDock");
        
        initializeButtons();
    }

    private void initializeButtons() {
        List<String> myButtons = myParser.getValuesFromTag("Buttons");
        for (String button:myButtons) {
            ControlDockButton dockButton = (ControlDockButton) Reflection.createInstance(button);
            dockButton.initialize(mySize);
            myTilePane.getChildren().add(dockButton);
        }
    }
    
    @Override
    public Node getNode () {
        return myTilePane;
    }
    
}

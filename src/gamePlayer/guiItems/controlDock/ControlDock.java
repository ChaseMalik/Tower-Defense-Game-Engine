package gamePlayer.guiItems.controlDock;

import java.io.File;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItems.headsUpDisplayB.GameStats;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.TilePane;
import utilities.XMLParsing.XMLParser;

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
        
    }
    
    
    @Override
    public Node getNode () {
        return myTilePane;
    }
    
}

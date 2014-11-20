package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import utilities.XMLParsing.XMLParser;

public class Store implements GuiItem {
    private TilePane myTilePane;
    private XMLParser myParser;
    private Dimension2D myPaneSize;
    private StoreListener myListener = GuiConstants.GUI_MANAGER;
    
    @Override
    public void initialize (Dimension2D containerSize) {
        myTilePane = new TilePane();
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        myPaneSize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
                                             containerSize.getHeight()*sizeRatio.getHeight());
        
        myTilePane.setMinSize(myPaneSize.getWidth(),myPaneSize.getHeight());
        myTilePane.setPrefSize(myPaneSize.getWidth(),myPaneSize.getHeight());
        myTilePane.getStyleClass().add("Store");
        
        myListener.registerStore(this);
    }

    @Override
    public Node getNode () {
        return myTilePane;
    }
}

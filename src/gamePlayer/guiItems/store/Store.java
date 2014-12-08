package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.TilePane;
import utilities.XMLParsing.XMLParser;

public class Store implements GuiItem {
    private TilePane myTilePane;
    private XMLParser myParser;
    private Dimension2D myPaneSize;
    private StoreListener myListener = GuiConstants.GUI_MANAGER;

    private Dimension2D buttonSize;
    private Dimension2D imageSize;
    
    private List<StoreItem> storeItemList;

    @Override
    public void initialize (Dimension2D containerSize) {
        myTilePane = new TilePane();
        String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        myPaneSize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
                                     containerSize.getHeight()*sizeRatio.getHeight());

        Dimension2D buttonRatio = myParser.getDimension("ButtonSize");
        
        buttonSize = new Dimension2D(myPaneSize.getWidth()*buttonRatio.getWidth(),
                                     myPaneSize.getWidth()*buttonRatio.getWidth());
        
        Dimension2D imageRatio = myParser.getDimension("ImageSize");
        imageSize =  new Dimension2D(buttonSize.getWidth()*imageRatio.getWidth(),
                                     buttonSize.getHeight()*imageRatio.getHeight());

        myTilePane.setMinSize(myPaneSize.getWidth(),myPaneSize.getHeight());
        myTilePane.setPrefSize(myPaneSize.getWidth(),myPaneSize.getHeight());
        myTilePane.getStyleClass().add("Store");
        myTilePane.toFront();

        myListener.registerStore(this);
    }

    public void fillStore(List<StoreItem> storeItems) {
        storeItemList = storeItems;
        myTilePane.getChildren().clear();
        for (StoreItem item:storeItems) {
            addStoreItem(item);
        }
        refreshStore();
    }

    private void addStoreItem(StoreItem storeItem) {
        Button button = new Button();
        button.setMinSize(buttonSize.getWidth(), buttonSize.getHeight());
        button.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());

        storeItem.getImageView().setFitHeight(imageSize.getHeight());
        storeItem.getImageView().setFitWidth(imageSize.getWidth());

        button.setGraphic(storeItem.getImageView());
        myTilePane.getChildren().add(button);
        button.setOnAction(event -> myListener.placeTower(storeItem.getName()));
    }
    
    public void refreshStore() {
        for (int k=0;k<storeItemList.size();k++) {
            if (storeItemList.get(k).availableBinding().getValue()) {
                Button button = (Button) myTilePane.getChildren().get(k);
                button.setDisable(false);
            } else {
                Button button = (Button) myTilePane.getChildren().get(k);
                button.setDisable(true);
            }
        }
    }

    @Override
    public Node getNode () {
        ScrollPane pane = new ScrollPane();
        pane.setContent(myTilePane);
        pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        pane.setHbarPolicy(ScrollBarPolicy.NEVER);
        return pane;
    }

	public void freeze() {
		myTilePane.setDisable(true);
	}
	
	public void unfreeze(){
		myTilePane.setDisable(false);
	}
}

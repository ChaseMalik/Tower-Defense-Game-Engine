package gamePlayer.guiItems.headsUpDisplay;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;

/**
 * This GuiItem initializes and manages the game statistics board (heads-up-display)
 * @author allankiplagat
 *
 */
public class HUD implements GuiItem {
    private XMLParser myParser;
    private TableView<GameStat> myTableView;
    private Dimension2D mySize;
    
    @Override
    public void initialize (Dimension2D containerSize) {
    	String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        
        myTableView = new TableView<GameStat>();
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        
        mySize = RatiosToDim.convert(containerSize, sizeRatio);
        
        myTableView.setMinSize(mySize.getWidth(),mySize.getHeight());
        myTableView.setPrefSize(mySize.getWidth(),mySize.getHeight());
        myTableView.getStyleClass().add("HUD");
        
        GuiConstants.GUI_MANAGER.registerStatsBoard(this);
    }
    
    /**
     * @param stats the list of GameStats objects to be displayed
     */
    public void setGameStats(List<GameStat> stats) {
        List<Double> colSizeRatio = myParser.getDoubleValuesFromTag("ColumnWidth");
        
        //convert list into observable list
        ObservableList<GameStat> statsList = FXCollections.observableArrayList(stats);
        myTableView.setItems(statsList);
        
        TableColumn<GameStat,String> statCol = new TableColumn<GameStat,String>("Stat");
        statCol.setCellValueFactory(new PropertyValueFactory("gameStat"));
        statCol.setPrefWidth(mySize.getWidth()*colSizeRatio.get(0));
        statCol.setResizable(false);
        
        TableColumn<GameStat,String> valueCol = new TableColumn<GameStat,String>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory("statValue"));
        valueCol.setPrefWidth(mySize.getWidth()*colSizeRatio.get(0));
        valueCol.setResizable(false);
        
        myTableView.getColumns().clear();
        myTableView.getColumns().setAll(statCol, valueCol);
    }

    @Override
    public Node getNode () {
        return myTableView;
    }
}

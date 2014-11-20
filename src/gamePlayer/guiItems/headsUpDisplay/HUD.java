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
import utilities.XMLParsing.XMLParser;

/**
 * This GuiItem initializes and manages the game statistics board (heads-up-display)
 * @author allankiplagat
 *
 */
public class HUD implements GuiItem {
    private XMLParser myParser;
    private TableView<GameStats> myTableView;
    private Dimension2D mySize;
    
    @Override
    public void initialize (Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 

        myTableView = new TableView<GameStats>();
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        
        mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
                                             containerSize.getHeight()*sizeRatio.getHeight());
        
        myTableView.setMinSize(mySize.getWidth(),mySize.getHeight());
        myTableView.setPrefSize(mySize.getWidth(),mySize.getHeight());
        myTableView.getStyleClass().add("HUD");
        
        GuiConstants.GUI_MANAGER.registerStatsBoard(this);
    }
    
    /**
     * @param stats the list of GameStats objects to be displayed
     */
    public void setGameStats(List<GameStats> stats) {
        List<Double> colSizeRatio = myParser.getDoubleValuesFromTag("ColumnWidth");
        
        //convert list into observable list
        ObservableList<GameStats> statsList = FXCollections.observableArrayList(stats);
        myTableView.setItems(statsList);
        
        TableColumn<GameStats,String> statCol = new TableColumn<GameStats,String>("Stat");
        statCol.setCellValueFactory(new PropertyValueFactory("gameStat"));
        statCol.setPrefWidth(mySize.getWidth()*colSizeRatio.get(0));
        statCol.setResizable(false);
        
        TableColumn<GameStats,String> valueCol = new TableColumn<GameStats,String>("Value");
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

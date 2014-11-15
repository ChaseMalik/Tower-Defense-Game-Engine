package gamePlayer.guiItems.statsBoard;

import gamePlayer.guiItems.GuiItem;
import java.io.File;
import java.util.List;
import utilities.XMLParsing.XMLParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StatsBoard implements GuiItem {
    private XMLParser myParser;
    private static TableView myTableView;
    
    @Override
    public void initialize (List<Double> containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 

        myTableView = new TableView<GameStats>();
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        //List<Double> mySize = 
        //myTableView.setPrefSize(sizeRatio.get(0)*containerSize.get(0), sizeRatio.get(1)*containerSize.get(1));
    }
    
    public static void setGameStats(List<GameStats> stats) {
        //convert list into observable list
        ObservableList<GameStats> statsList = FXCollections.observableArrayList(stats);
        myTableView.setItems(statsList);
        
        TableColumn<GameStats,String> statCol = new TableColumn<GameStats,String>("Stat");
        statCol.setCellValueFactory(new PropertyValueFactory("gameStat"));
        //statCol.setPrefWidth(arg0);
        TableColumn<GameStats,String> valueCol = new TableColumn<GameStats,String>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory("statValue"));
        
        myTableView.getColumns().setAll(statCol, valueCol);
        
    }

    @Override
    public Node getNode () {
        return myTableView;
    }
}

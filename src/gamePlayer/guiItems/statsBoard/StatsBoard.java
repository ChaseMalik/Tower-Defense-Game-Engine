package gamePlayer.guiItems.statsBoard;

import gamePlayer.guiItems.GuiItem;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

public class StatsBoard implements GuiItem {
    private XMLParser myParser;
    private static TableView myTableView;
    
    @Override
    public void initialize (List<Double> containerSize) {
        myParser = XMLParserInstantiator.getInstance(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));

        myTableView = new TableView();
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        myTableView.setPrefSize(sizeRatio.get(0)*containerSize.get(0), sizeRatio.get(1)*containerSize.get(1));
    }
    
    public static void setGameStats(List<GameStats> stats) {
        //convert list into observable list
        ObservableList<GameStats> statsList = FXCollections.observableArrayList(stats);
        myTableView.setItems(statsList);
        
        TableColumn<GameStats,String> firstNameCol = new TableColumn<GameStats,String>("Stat");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("gameStat"));
        TableColumn<GameStats,String> lastNameCol = new TableColumn<GameStats,String>("Value");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("statValue"));
        
        myTableView.getColumns().setAll(firstNameCol, lastNameCol);
    }

    @Override
    public Node getNode () {
        return myTableView;
    }
}

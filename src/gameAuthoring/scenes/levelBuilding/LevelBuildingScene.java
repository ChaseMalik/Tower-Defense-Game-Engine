package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.FileMenu;
import gameEngine.actors.BaseActor;
import gameEngine.levels.BaseLevel;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class LevelBuildingScene extends BuildingScene implements Observer {
    
    private static final String TITLE = "Level";
    
    private List<BaseActor> myEnemies;
    private ObservableList<BaseLevel>  myLevels;
    private ListView<BaseLevel> myLevelListView;

    public LevelBuildingScene (BorderPane root, List<BaseActor> enemies) {
        super(root, TITLE);
        myEnemies = enemies;
        setupMenu();     
        setupLevelListView();
        
    }

    private void setupLevelListView () {
        myLevelListView = new ListView<BaseLevel>();      
        myLevels = FXCollections.observableArrayList();
        myPane.setCenter(myLevelListView);
    }

    private void setupMenu () {
        FileMenu menu = new FileMenu();
        menu.addObserver(this);
        myPane.setTop(menu.getNode());
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        this.setChanged();
        this.notifyObservers(myLevels);    
    }
}

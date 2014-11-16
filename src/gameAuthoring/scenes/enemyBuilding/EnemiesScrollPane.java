package gameAuthoring.scenes.enemyBuilding;

import gameEngine.actors.BaseBaddie;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class EnemiesScrollPane extends Observable {
    private ListView<BaseBaddie> myEnemies;
    
    public EnemiesScrollPane() {
//        myEnemies = new ListView<BaseBaddie>();
//        BaseBaddie baddie1 = new BaseBaddie();
//        ObservableList<BaseBaddie> items = FXCollections.observableArrayList();
//        list.setItems(items);  
//        list.setPrefWidth(ENEMY_LIST_WIDTH);
    }
}

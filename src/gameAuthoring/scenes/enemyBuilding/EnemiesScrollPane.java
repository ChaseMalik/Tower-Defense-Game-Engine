package gameAuthoring.scenes.enemyBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

public class EnemiesScrollPane extends ScrollPane {
    
    private ObservableList<BaseActor> myEnemies;
    private ListView<BaseActor> myEnemiesListView;
    
    public EnemiesScrollPane(List<BaseActor> enemies) {
        myEnemies = (ObservableList<BaseActor>) enemies;
        myEnemiesListView = new ListView<BaseActor>();
        myEnemiesListView.setItems(myEnemies);
        myEnemiesListView.setPrefSize(EnemyBuildingScene.ENEMY_IMG_WIDTH + 15, AuthorController.SCREEN_HEIGHT - 7);
        this.setContent(myEnemiesListView);
    }
}

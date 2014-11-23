package gameAuthoring.scenes.actorBuildingScenes.actorListView;

import gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells.EnemyCell;
import gameEngine.actors.BaseEnemy;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Represents the ListView in the TowerBuildingScene that allows the user
 * to specify which enemies a tower can shoot.
 * @author Austin Kyker
 *
 */
public class EnemySelectionDisplay extends ListView<BaseEnemy> {

    private static final int ENEMY_WIDTH = 70;
    private static final int ENEMY_HEIGHT = 70;
    private ObservableList<BaseEnemy> myEnemies;

    public EnemySelectionDisplay(List<BaseEnemy> enemies) {
        myEnemies = (ObservableList<BaseEnemy>) enemies;
        this.setOrientation(Orientation.HORIZONTAL);
        setItems(myEnemies);
        setCellFactory(new Callback<ListView<BaseEnemy>, 
                       ListCell<BaseEnemy>>() {
            @Override 
            public ListCell<BaseEnemy> call(ListView<BaseEnemy> list) {
                return new EnemyCell(ENEMY_WIDTH, ENEMY_HEIGHT);
            }
        });
    }
}
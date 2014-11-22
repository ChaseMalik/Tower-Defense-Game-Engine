package gameAuthoring.scenes.actorBuildingScenes.actorListView;

import gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells.EnemyCell;
import gameEngine.actors.BaseEnemy;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;

/**
 * Displays the actors (enemies or towers) that have already been created and will
 * be used in the game in a ListView.
 * @author Austin Kyker
 *
 */
public class CreatedEnemiesDisplay extends ListView<BaseEnemy> {

    private static final int ENEMY_WIDTH = 150;
    private static final int ENEMY_HEIGHT = 150;
    private ObservableList<BaseEnemy> myActors;

    public CreatedEnemiesDisplay(List<BaseEnemy> myEnemies) {
        this.setPrefWidth(ENEMY_WIDTH);
        myActors = (ObservableList<BaseEnemy>) myEnemies;
        setItems(myActors);
        setCellFactory(new Callback<ListView<BaseEnemy>, 
                       ListCell<BaseEnemy>>() {
            @Override 
            public ListCell<BaseEnemy> call(ListView<BaseEnemy> list) {
                EnemyCell enemyCell = new EnemyCell(ENEMY_WIDTH, ENEMY_HEIGHT);
                MenuItem delete = new MenuItem("Delete Actor");
                delete.setOnAction(event->myActors.remove(enemyCell.getItem()));
                ContextMenu contextMenu = new ContextMenu(delete);
                enemyCell.setContextMenu(contextMenu);
                return enemyCell;
            }
        });
    }
}
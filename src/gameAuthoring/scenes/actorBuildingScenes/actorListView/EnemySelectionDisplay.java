package gameAuthoring.scenes.actorBuildingScenes.actorListView;

import gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells.ActorCell;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class EnemySelectionDisplay extends ListView<BaseActor> {

    private static final int ENEMY_WIDTH = 70;
    private static final int ENEMY_HEIGHT = 70;
    private ObservableList<BaseActor> myEnemies;

    public EnemySelectionDisplay(List<BaseActor> enemies) {
        myEnemies = (ObservableList<BaseActor>) enemies;
        this.setOrientation(Orientation.HORIZONTAL);
        setItems(myEnemies);
        setCellFactory(new Callback<ListView<BaseActor>, 
                       ListCell<BaseActor>>() {
            @Override 
            public ListCell<BaseActor> call(ListView<BaseActor> list) {
                return new ActorCell(ENEMY_WIDTH, ENEMY_HEIGHT);
            }
        });
    }
}
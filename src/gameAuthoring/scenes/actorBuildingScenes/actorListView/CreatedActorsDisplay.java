package gameAuthoring.scenes.actorBuildingScenes.actorListView;

import gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells.ActorCell;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CreatedActorsDisplay extends ListView<BaseActor> {

    private static final int ENEMY_WIDTH = 150;
    private static final int ENEMY_HEIGHT = 150;
    private ObservableList<BaseActor> myActors;

    public CreatedActorsDisplay(List<BaseActor> actors) {
        this.setPrefWidth(ENEMY_WIDTH);
        myActors = (ObservableList<BaseActor>) actors;
        setItems(myActors);
        setCellFactory(new Callback<ListView<BaseActor>, 
                       ListCell<BaseActor>>() {
            @Override 
            public ListCell<BaseActor> call(ListView<BaseActor> list) {
                return new ActorCell(ENEMY_WIDTH, ENEMY_HEIGHT);
            }
        });
    }
}
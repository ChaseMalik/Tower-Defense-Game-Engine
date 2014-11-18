package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CreatedActorsScrollPane extends ScrollPane {
    
    private ObservableList<BaseActor> myActors;
    private ListView<BaseActor> myActorsListView;
    
    public CreatedActorsScrollPane(List<BaseActor> actors) {
        myActors = (ObservableList<BaseActor>) actors;
        myActorsListView = new ListView<BaseActor>();
        myActorsListView.setItems(myActors);
        myActorsListView.setPrefSize(ActorBuildingScene.ACTOR_IMG_WIDTH + 15, AuthorController.SCREEN_HEIGHT - 7);
        myActorsListView.setCellFactory(new Callback<ListView<BaseActor>, 
                            ListCell<BaseActor>>() {
                                @Override 
                                public ListCell<BaseActor> call(ListView<BaseActor> list) {
                                    return new EnemyCell();
                                }
                            }
                        );
        this.setContent(myActorsListView);
    }
    
    private class EnemyCell extends ListCell<BaseActor> {
        @Override
        public void updateItem(BaseActor item, boolean empty) {
            super.updateItem(item, empty);
            if(item != null) {
                VBox actorCellContainer = new VBox(5);
                actorCellContainer.setAlignment(Pos.CENTER);
                Label removeLabel = new Label("X");
                removeLabel.getStyleClass().add("removeActorLabel");
                removeLabel.setOnMouseClicked(event->myActors.remove(item));
                Label enemyNameLabel = new Label(item.toString());
                actorCellContainer.getChildren().addAll(removeLabel,
                                                    new ImageView(item.getImage()), 
                                                    enemyNameLabel);                
                setGraphic(actorCellContainer);
            }
        }
    }
}
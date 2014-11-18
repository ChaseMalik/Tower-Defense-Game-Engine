package gameAuthoring.scenes.enemyBuilding;

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

public class EnemiesScrollPane extends ScrollPane {
    
    private ObservableList<BaseActor> myEnemies;
    private ListView<BaseActor> myEnemiesListView;
    
    public EnemiesScrollPane(List<BaseActor> enemies) {
        myEnemies = (ObservableList<BaseActor>) enemies;
        myEnemiesListView = new ListView<BaseActor>();
        myEnemiesListView.setItems(myEnemies);
        myEnemiesListView.setPrefSize(EnemyBuildingScene.ENEMY_IMG_WIDTH + 15, AuthorController.SCREEN_HEIGHT - 7);
        myEnemiesListView.setCellFactory(new Callback<ListView<BaseActor>, 
                            ListCell<BaseActor>>() {
                                @Override 
                                public ListCell<BaseActor> call(ListView<BaseActor> list) {
                                    return new EnemyCell();
                                }
                            }
                        );
        this.setContent(myEnemiesListView);
    }
    
    private class EnemyCell extends ListCell<BaseActor> {
        @Override
        public void updateItem(BaseActor item, boolean empty) {
            super.updateItem(item, empty);
            if(item != null) {
                VBox enemyContainer = new VBox(5);
                enemyContainer.setAlignment(Pos.CENTER);
                Label removeLabel = new Label("X");
                removeLabel.getStyleClass().add("removeEnemyLabel");
                removeLabel.setOnMouseClicked(event->myEnemies.remove(item));
                Label enemyNameLabel = new Label(item.toString());
                enemyContainer.getChildren().addAll(removeLabel,
                                                    new ImageView(item.getImage()), 
                                                    enemyNameLabel);                
                setGraphic(enemyContainer);
                
            }
        }
    }
}

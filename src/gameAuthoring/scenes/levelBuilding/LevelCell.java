package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseActor;
import gameEngine.levels.BaseLevel;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelCell extends ListCell<BaseLevel> {
    
    private List<BaseActor> myEnemies;
    
    public LevelCell(List<BaseActor> enemies) {
        myEnemies = enemies;
    }

    @Override
    public void updateItem(BaseLevel item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null && !empty) {
            HBox enemiesContainer = new HBox(5);
            for(int i = 0; i < myEnemies.size(); i++) {
                VBox enemyBox = new VBox();
                BaseActor enemy = myEnemies.get(i);
                TextField numTextField = new TextField();
                enemyBox.getChildren().addAll(new Label(enemy.toString()), enemy, numTextField);
                enemiesContainer.getChildren().add(enemyBox);                
            }
            setGraphic(enemiesContainer);
        }
    }
}

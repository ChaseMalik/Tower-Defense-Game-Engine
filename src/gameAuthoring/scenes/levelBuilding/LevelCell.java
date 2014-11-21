package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameEngine.actors.BaseActor;
import gameEngine.levels.BaseLevel;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelCell extends ListCell<BaseLevel> {

    private static final double FIT_SIZE = 80;
    private static final int WIDTH_BTW_ENEMIES = 15;

    private List<BaseActor> myEnemies;

    public LevelCell(List<BaseActor> enemies) {
        myEnemies = enemies;
    }

    @Override
    public void updateItem(BaseLevel item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null && !empty) {
            this.setAlignment(Pos.CENTER);
            VBox levelContainer = new VBox(10);         
            HBox enemiesContainer = new HBox(WIDTH_BTW_ENEMIES);
            for(int i = 0; i < myEnemies.size(); i++) {
                VBox enemyBox = new VBox(5);
                BaseActor enemy = myEnemies.get(i);
                TextField numTextField = buildTextField(item, enemy);
                ImageView enemyImg = buildImageView(enemy);
                enemyBox.getChildren().addAll(new Label(enemy.toString()), enemyImg, numTextField);
                enemiesContainer.getChildren().add(enemyBox);                
            }
            levelContainer.getChildren().addAll(createLevelLabelAndTimeDisplay(), enemiesContainer);
            setGraphic(levelContainer);
        }
    }
    
    private BorderPane createLevelLabelAndTimeDisplay(){
    	BorderPane labelAndTime = new BorderPane();
    	HBox timeAndSeconds = new HBox(10);
    	Label levelLabel = new Label("Level " + this.getIndex() +1);
    	
    	TextField levelTime = new TextField();
    	levelTime.setPrefWidth(50);
    	Label secondsLabel = new Label("seconds");
    	timeAndSeconds.getChildren().addAll(levelTime, secondsLabel);
    	
    	labelAndTime.setRight(timeAndSeconds);
    	labelAndTime.setLeft(levelLabel);
    	
    	return labelAndTime;
    }

    

    private ImageView buildImageView (BaseActor enemy) {
        ImageView enemyImg = new ImageView(enemy.getImage());
        enemyImg.setFitHeight(FIT_SIZE);
        enemyImg.setFitWidth(FIT_SIZE);
        return enemyImg;
    }

    private TextField buildTextField (BaseLevel level, BaseActor enemy) {
        TextField numTextField = new TextField();
        numTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    int numOfEnemyType = Integer.parseInt(newValue);
                    level.getMap().put(enemy, numOfEnemyType);
                } catch (NumberFormatException e) {
                    if(numTextField.getText().isEmpty()) {
                        numTextField.setText("");
                    }
                    else {
                        numTextField.setText(oldValue);
                    }
                }
            }
        });
        return numTextField;
    }
}

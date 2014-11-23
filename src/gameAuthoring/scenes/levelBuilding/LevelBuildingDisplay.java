package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.ArrayList;
import java.util.List;
import utilities.StringToImageViewConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelBuildingDisplay extends VBox {
    
    private static final String LVL_CONTAINER_CLASS = "levelContainer";
    private static final int SPACE_BTW_LEVEL_INFO_AND_ENEMIES = 50;
    private static final int TEXT_FIELD_WIDTH = 50;
    private static final double FIT_SIZE = 80;
    private static final int GENERAL_PADDING = 10;
    
    private List<BaseLevel> myLevels;
    private List<BaseEnemy> myEnemies;
    
    public LevelBuildingDisplay(List<BaseEnemy> enemies) {
        myEnemies = enemies;
        myLevels = new ArrayList<BaseLevel>();
    }
    
    public void addLevel(BaseLevel level) {
        myLevels.add(level);
        makeNewLevelContainer(level);
    }

    private void makeNewLevelContainer (BaseLevel level) {
        HBox container = new HBox(SPACE_BTW_LEVEL_INFO_AND_ENEMIES);
        container.setPadding(new Insets(GENERAL_PADDING));
        container.getStyleClass().add(LVL_CONTAINER_CLASS);
        VBox levelInfoBox = createLevelInfoBox(level);        
        HBox enemiesBox = createEnemiesBox(level);
        container.getChildren().addAll(levelInfoBox, enemiesBox);  
        this.getChildren().add(container);
    }

    private HBox createEnemiesBox (BaseLevel level) {
        HBox enemiesBox = new HBox(GENERAL_PADDING);
        for(BaseEnemy enemy:myEnemies) {
            VBox enemyBox = new VBox(5);
            enemyBox.setAlignment(Pos.CENTER);
            Label enemyNameLabel = new Label(enemy.toString());
            ImageView enemyImg = 
                    StringToImageViewConverter.getImageView(FIT_SIZE, 
                                                            FIT_SIZE, 
                                                            enemy.getImagePath());
            enemyImg.setFitHeight(FIT_SIZE);
            enemyImg.setFitWidth(FIT_SIZE);         
            enemyBox.getChildren().addAll(enemyNameLabel, enemyImg, 
                                          buildTextField(level, enemy));
            enemiesBox.getChildren().add(enemyBox);
        }
        return enemiesBox;
    }

    private VBox createLevelInfoBox (BaseLevel level) {
        VBox levelInfoBox = new VBox(10);
        levelInfoBox.setPadding(new Insets(15));
        HBox timeAndSeconds = new HBox(10);
        TextField levelTime = new TextField();
        levelTime.setPrefWidth(TEXT_FIELD_WIDTH);
        Label secondsLabel = new Label("seconds");
        timeAndSeconds.getChildren().addAll(levelTime, secondsLabel);
        Label levelLabel = new Label("Level " + (myLevels.indexOf(level)+1));
        levelLabel.getStyleClass().add("levelLabel");
        levelInfoBox.getChildren().addAll(levelLabel,
                                          timeAndSeconds);
        return levelInfoBox;
    }
    
    /**
     * This text field only allows numbers to be typed.
     */
    private TextField buildTextField (BaseLevel level, BaseEnemy enemy) {
        TextField numTextField = new TextField();
        numTextField.setPrefWidth(TEXT_FIELD_WIDTH);
        numTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    int numOfEnemyType = Integer.parseInt(newValue);
                    level.getEnemyMap().put(enemy, numOfEnemyType);
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

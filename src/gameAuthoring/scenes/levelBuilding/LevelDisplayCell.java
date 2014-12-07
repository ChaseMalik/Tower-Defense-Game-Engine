package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.JavaFXutilities.numericalTextFields.NumericalTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LevelDisplayCell extends HBox {

    private static final String LVL_CONTAINER_CLASS = "levelContainer";
    private static final int SPACE_BTW_LEVEL_INFO_AND_ENEMIES = 50;
    private static final int TEXT_FIELD_WIDTH = 50;
    private static final double FIT_SIZE = 80;
    private static final int GENERAL_PADDING = 10;

    private List<BaseEnemy> myEnemies;
    private int myLevelNum;
    private Map<BaseEnemy, NumericalTextField> enemyToEnemyCountMap;
    private NumericalTextField myLevelDurationField;

    public LevelDisplayCell (List<BaseEnemy> baseEnemies, int levelNum) {
        myEnemies = baseEnemies;
        myLevelNum = levelNum;
        enemyToEnemyCountMap = new HashMap<BaseEnemy, NumericalTextField>();
        HBox container = new HBox(SPACE_BTW_LEVEL_INFO_AND_ENEMIES);
        container.setPadding(new Insets(GENERAL_PADDING));
        container.getStyleClass().add(LVL_CONTAINER_CLASS);
        VBox levelInfoBox = createLevelInfoBox();
        HBox enemiesBox = createEnemiesBox();
        container.getChildren().addAll(levelInfoBox, enemiesBox);
        this.getChildren().add(container);
    }

    private HBox createEnemiesBox () {
        HBox enemiesBox = new HBox(GENERAL_PADDING);
        for (BaseEnemy enemy : myEnemies) {
            VBox enemyBox = new VBox(5);
            enemyBox.setAlignment(Pos.CENTER);
            Label enemyNameLabel = new Label(enemy.toString());
            ImageView enemyImg =
                    StringToImageViewConverter.getImageView(FIT_SIZE,
                                                            FIT_SIZE,
                                                            enemy.getImagePath());
            enemyImg.setFitHeight(FIT_SIZE);
            enemyImg.setFitWidth(FIT_SIZE);
            NumericalTextField enemiesCountField = new NumericalTextField(TEXT_FIELD_WIDTH);
            enemyToEnemyCountMap.put(enemy, enemiesCountField);
            enemyBox.getChildren().addAll(enemyNameLabel, enemyImg,
                                          enemiesCountField);
            enemiesBox.getChildren().add(enemyBox);
        }
        return enemiesBox;
    }

    private VBox createLevelInfoBox () {
        VBox levelInfoBox = new VBox(10);
        levelInfoBox.setPadding(new Insets(15));
        HBox timeAndSeconds = new HBox(10);
        myLevelDurationField = new NumericalTextField(TEXT_FIELD_WIDTH);
        Label secondsLabel = new Label("seconds");
        timeAndSeconds.getChildren().addAll(myLevelDurationField, secondsLabel);
        Label levelLabel = new Label("Level " + myLevelNum);
        levelLabel.getStyleClass().add("levelLabel");
        levelInfoBox.getChildren().addAll(levelLabel,
                                          timeAndSeconds);
        return levelInfoBox;
    }

    public BaseLevel generateLevel () {
        BaseLevel level = new BaseLevel();
        for (BaseEnemy enemy : myEnemies) {
            level.addEnemyCountPair(new EnemyCountPair(enemyToEnemyCountMap.get(enemy).getNumber(),
                                                       enemy));
        }
        level.setDuration(myLevelDurationField.getNumber());
        return level;
    }

    public boolean isUserInputValid () {
        return !isAnyEnemyFieldEmpty() && !myLevelDurationField.getText().isEmpty();
    }

    private boolean isAnyEnemyFieldEmpty () {
        return enemyToEnemyCountMap.values()
                .stream()
                .filter(field -> field.getText().isEmpty())
                .count() > 0;
    }
}

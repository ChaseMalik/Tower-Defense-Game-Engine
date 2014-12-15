package gameAuthoring.scenes.levelBuilding;

import gameAuthoring.mainclasses.Constants;
import gameEngine.MainEngineManager;
import gameEngine.actors.BaseEnemy;
import gameEngine.levels.BaseLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import utilities.JavaFXutilities.numericalTextFields.NumericalTextField;
import utilities.multilanguage.MultiLanguageUtility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class LevelDisplayCell extends HBox {

    private static final String LVL_CONTAINER_CLASS = "levelContainer";
    private static final int SPACE_BTW_LEVEL_INFO_AND_ENEMIES = 50;
    private static final int TEXT_FIELD_WIDTH = 50;
    private static final double FIT_SIZE = 80;
    private static final int GENERAL_PADDING = 10;
    private static final String PLAYLEVELBUTTON_IMG_PATH =
            "gameAuthoring/scenes/levelBuilding/PlayLevelButton.png";

    private List<BaseEnemy> myEnemies;
    private int myLevelNum;
    private Map<BaseEnemy, NumericalTextField> enemyToEnemyCountMap;
    private NumericalTextField myLevelDurationField;
    private boolean myEngineInstantiatedFlag;
    private MainEngineManager myEngineManager;

    public LevelDisplayCell (List<BaseEnemy> baseEnemies, int levelNum, Pane pane) {
        myEnemies = baseEnemies;
        myLevelNum = levelNum;
        enemyToEnemyCountMap = new HashMap<BaseEnemy, NumericalTextField>();
        HBox container = new HBox(SPACE_BTW_LEVEL_INFO_AND_ENEMIES);
        container.setPadding(new Insets(GENERAL_PADDING));
        container.getStyleClass().add(LVL_CONTAINER_CLASS);
        VBox levelInfoBox = createLevelInfoBox();
        HBox enemiesBox = createEnemiesBox();
        Image img = new Image(PLAYLEVELBUTTON_IMG_PATH);
        ImageView playLevelButton = new ImageView(img);
        myEngineInstantiatedFlag = false;
        playLevelButton.setOnMouseClicked(event -> {
            if (!myEngineInstantiatedFlag) {
                myEngineManager = new MainEngineManager(pane);
                myEngineInstantiatedFlag = true;
            }
            if (!myEngineManager.isRunning()) {
                myEngineManager.loadAuthoringLevel(generateLevel());
                myEngineManager.resume();
            }

        });
        container.getChildren().addAll(levelInfoBox, enemiesBox, playLevelButton);
        this.getChildren().add(container);
    }

    private HBox createEnemiesBox () {
        HBox enemiesBox = new HBox(GENERAL_PADDING);
        for (BaseEnemy enemy : myEnemies) {
            VBox enemyBox = new VBox(Constants.SMALLEST_PADDING);
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
        VBox levelInfoBox = new VBox(Constants.SM_PADDING);
        levelInfoBox.setPadding(new Insets(Constants.MED_PADDING));
        HBox timeAndSeconds = new HBox(Constants.SM_PADDING);
        myLevelDurationField = new NumericalTextField(TEXT_FIELD_WIDTH);
        Label secondsLabel = new Label();
        secondsLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.SECONDS));
        timeAndSeconds.getChildren().addAll(myLevelDurationField, secondsLabel);
        Label levelLabel = new Label("Level " + (myLevelNum + 1));
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
        return isAllEnemyFieldsValid() && myLevelDurationField.isValueEntered() &&
               myLevelDurationField.getNumber() > 0;
    }

    private boolean isAllEnemyFieldsValid () {
        return enemyToEnemyCountMap.values()
                .stream()
                .filter(field -> !(field.isValueEntered() && field.getNumber() >= 0))
                .count() == 0;
    }
}

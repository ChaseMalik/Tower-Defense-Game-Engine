package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorMapBuilder;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.ProjectileInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropCopyImagePane;
import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropImagePane;
import utilities.JavaFXutilities.slider.SliderContainer;
import utilities.XMLParsing.XMLParser;
import utilities.multilanguage.MultiLanguageUtility;


public class ProjectilePane extends Observable implements Observer {

    private static final String TITLE_CSS = "title";
    private static final String RIGHT_BOX_CSS = "projectileRightBox";
    private static final int LARGE_PADDING = 20;
    private static final int SMALL_PADDING = 10;
    private static final int PROJECTILE_IMG_HEIGHT = 80;
    private static final int PROJECTILE_IMG_WIDTH = 100;
    private static final String DRAG_N_DROP_CSS = "borderedPane";
    private static final String PROJECTILE_IMG_DIR =
            AuthorController.gameDir + "projectileImages/";
    private static final String PROJECTILE_BEHAVIORS_XML =
            "./src/gameAuthoring/Resources/actorBehaviors/ProjectileBehaviors.xml";
    private VBox myContainer;
    private DragAndDropImagePane myDropImgPane;
    private ArrayList<BehaviorBuilder> myBehaviorBuilders;
    private SliderContainer myDamageSlider;

    public ProjectilePane () {

        File dir = new File(PROJECTILE_IMG_DIR);
        dir.mkdir();

        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        XMLParser parser = new XMLParser(new File(PROJECTILE_BEHAVIORS_XML));
        List<String> allBehaviorTypes = parser.getAllBehaviorTypes();
        for (String behaviorType : allBehaviorTypes) {
            List<String> behaviorOptions = parser.getBehaviorElementsFromTag(behaviorType);
            myBehaviorBuilders.add(new BehaviorBuilder(behaviorType, behaviorOptions,
                                                       parser.getSliderInfo(behaviorType)));
        }

        HBox behaviorBox = new HBox(SMALL_PADDING);
        for (BehaviorBuilder builder : myBehaviorBuilders) {
            behaviorBox.getChildren().add(builder.getContainer());
        }

        myContainer = new VBox(LARGE_PADDING);
        myContainer.setPadding(new Insets(SMALL_PADDING));

        Label title = new Label();
        title.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.PROJECTILE));
        title.getStyleClass().add(TITLE_CSS);

        myDropImgPane = new DragAndDropCopyImagePane(PROJECTILE_IMG_WIDTH,
                                                     PROJECTILE_IMG_HEIGHT,
                                                     PROJECTILE_IMG_DIR);
        myDropImgPane.getPane().getStyleClass().add(DRAG_N_DROP_CSS);
        VBox rightBox = new VBox(10);
        rightBox.setMaxWidth(120);
        myDamageSlider = new SliderContainer(Constants.DAMAGE, 1, 5);
        rightBox.getChildren().addAll(myDamageSlider, myDropImgPane.getPane());
        rightBox.getStyleClass().add(RIGHT_BOX_CSS);
        behaviorBox.getChildren().add(rightBox);
        myContainer.getChildren().addAll(title, behaviorBox);
    }

    public VBox getNode () {
        return myContainer;
    }

    public ProjectileInfo makeProjectileInfo (List<BaseEnemy> enemiesCanDamage) {
        List<String> enemyStrings = enemiesCanDamage.stream()
                .map(enemy -> enemy.toString())
                .collect(Collectors.toList());
        return new ProjectileInfo(myDropImgPane.getImagePath(),
                                  (int) myDamageSlider.getSliderValue(),
                                  BehaviorMapBuilder.buildMap(myBehaviorBuilders),
                                  enemyStrings);
    }

    /**
     * Called when Projectile image is dragged in.
     */
    @Override
    public void update (Observable arg0, Object arg1) {
        myContainer.getChildren().remove(myDropImgPane.getPane());
    }

    public boolean isInfoValid () {
        return myBehaviorBuilders.stream()
                .filter(builder -> !builder.isValid())
                .count() == 0;
    }
}

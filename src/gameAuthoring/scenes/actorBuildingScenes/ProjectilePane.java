package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
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

public class ProjectilePane extends Observable implements Observer {

    private static final String PROJECTILE_IMG_DIR = 
            AuthorController.gameDir + "projectileImages/";
    private static final String PROJECTILE_BEHAVIORS_XML = 
            "./src/gameAuthoring/Resources/actorBehaviors/ProjectileBehaviors.xml";
    private VBox myContainer;
    private DragAndDropImagePane myDropImgPane;
    private ArrayList<BehaviorBuilder> myBehaviorBuilders;
    private SliderContainer myDamageSlider;

    public ProjectilePane() {

        File dir = new File(PROJECTILE_IMG_DIR);
        dir.mkdir();

        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        XMLParser parser = new XMLParser(new File(PROJECTILE_BEHAVIORS_XML));
        List<String> allBehaviorTypes = parser.getAllBehaviorTypes();
        for(String behaviorType:allBehaviorTypes){
            List<String> behaviorOptions = parser.getValuesFromTag(behaviorType);
            myBehaviorBuilders.add(new BehaviorBuilder(behaviorType, behaviorOptions, 
                                                       parser.getSliderInfo(behaviorType)));
        }

        HBox behaviorBox = new HBox(10);
        for(BehaviorBuilder builder:myBehaviorBuilders) {
            behaviorBox.getChildren().add(builder.getContainer());
        }


        myContainer = new VBox(20);
        myContainer.setPadding(new Insets(10));

        Label title = new Label("Projectile");
        title.setStyle("-fx-font-size: 18px");

        myDropImgPane = new DragAndDropCopyImagePane(100, 80, PROJECTILE_IMG_DIR);
        myDropImgPane.getPane().getStyleClass().add("borderedPane");
        VBox rightBox = new VBox(10);
        rightBox.setMaxWidth(120);
        myDamageSlider = new SliderContainer("Damage", 1, 5);
        rightBox.getChildren().addAll(myDamageSlider, myDropImgPane.getPane());
        rightBox.setStyle("-fx-border-width: 1px; -fx-border-color: gray; " +
                          "-fx-padding: 10px; -fx-border-radius: 5px");
        behaviorBox.getChildren().add(rightBox);
        myContainer.getChildren().addAll(title, behaviorBox);        
    }

    public VBox getNode () {
        return myContainer;
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        myContainer.getChildren().remove(myDropImgPane.getPane());             
    }

    public ProjectileInfo makeProjectileInfo(List<BaseEnemy> enemiesCanDamage) {
        List<String> enemyStrings = enemiesCanDamage
                .stream()
                .map(enemy->enemy.toString())
                .collect(Collectors.toList());
        return new ProjectileInfo(myDropImgPane.getImagePath(),
                                  (int) myDamageSlider.getSliderValue(),
                                  null,
                                  enemyStrings);
    }
}
